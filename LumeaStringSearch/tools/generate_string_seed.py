#!/usr/bin/env python3
"""
Regenerates `StringSeedData.kt` from the Lumea localization workbook.

Source of truth
---------------
Workbook : shared/src/commonMain/kotlin/com/example/lumeastringsearch/database/LumeaLocalization1oct.xlsx
Sheet    : "Shopping Cart"
Columns  : A = New String   -> StringEntity.key
           D = Android_Key  -> StringEntity.isAndroid (non-blank => true)
           C = Common_Key   -> StringEntity.isIos     (non-blank => true)
           E = English      -> StringEntity.value

`rowNumber` is the 1-based worksheet row (same number Excel shows in the gutter),
so an entry can always be traced straight back to the sheet.

Usage
-----
    pip install openpyxl
    python3 tools/generate_string_seed.py
"""

from __future__ import annotations

import sys
import warnings
from pathlib import Path

try:
    import openpyxl
except ImportError:  # pragma: no cover
    sys.exit("openpyxl is required: pip install openpyxl")

warnings.filterwarnings("ignore", category=UserWarning, module="openpyxl")

REPO_ROOT = Path(__file__).resolve().parents[1]
COMMON_MAIN = REPO_ROOT / "shared/src/commonMain/kotlin/com/example/lumeastringsearch"
WORKBOOK = COMMON_MAIN / "database/LumeaLocalization1oct.xlsx"
OUTPUT = COMMON_MAIN / "data/local/StringSeedData.kt"

SHEET = "Shopping Cart"
COL_KEY = 0        # A - New String
COL_COMMON = 2     # C - Common_Key   (iOS)
COL_ANDROID = 3    # D - Android_Key
COL_ENGLISH = 4    # E - English
HEADER_ROWS = 1

HEADER = '''/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

/**
 * Auto-generated seed data from LumeaLocalization1oct.xlsx (sheet "{sheet}").
 * Do not edit by hand - run `python3 tools/generate_string_seed.py` instead.
 *
 * `rowNumber` is the 1-based worksheet row the entry came from.
 * Total entries: {count}
 */
object StringSeedData {{
    val initialStrings: List<StringEntity> = listOf(
'''

FOOTER = """    )
}
"""

ESCAPES = {
    "\\": "\\\\",
    '"': '\\"',
    "$": "\\$",
    "\n": "\\n",
    "\r": "\\r",
    "\t": "\\t",
}


def kotlin_string(raw: str) -> str:
    """Escape to an ASCII-safe Kotlin string literal (non-ASCII becomes \\uXXXX)."""
    out = []
    for ch in raw:
        if ch in ESCAPES:
            out.append(ESCAPES[ch])
        elif ord(ch) < 0x20 or ord(ch) > 0x7E:
            if ord(ch) > 0xFFFF:  # astral plane -> surrogate pair
                cp = ord(ch) - 0x10000
                out.append(f"\\u{0xD800 + (cp >> 10):04x}\\u{0xDC00 + (cp & 0x3FF):04x}")
            else:
                out.append(f"\\u{ord(ch):04x}")
        else:
            out.append(ch)
    return "".join(out)


def cell(value) -> str:
    return "" if value is None else str(value).strip()


def main() -> int:
    if not WORKBOOK.exists():
        sys.exit(f"Workbook not found: {WORKBOOK}")

    wb = openpyxl.load_workbook(WORKBOOK, read_only=True, data_only=True)
    ws = wb[SHEET]

    lines: list[str] = []
    seen: set[str] = set()
    for row_number, row in enumerate(ws.iter_rows(values_only=True), start=1):
        if row_number <= HEADER_ROWS:
            continue
        key = cell(row[COL_KEY])
        if not key or key in seen:
            continue
        seen.add(key)

        value = cell(row[COL_ENGLISH])
        is_android = bool(cell(row[COL_ANDROID]))
        is_ios = bool(cell(row[COL_COMMON]))

        lines.append(
            f'    StringEntity(key = "{kotlin_string(key)}", '
            f'value = "{kotlin_string(value)}", '
            f"isAndroid = {str(is_android).lower()}, "
            f"isIos = {str(is_ios).lower()}, "
            f"rowNumber = {row_number}),"
        )

    content = (
        HEADER.format(sheet=SHEET, count=len(lines))
        + "\n".join(lines)
        + "\n"
        + FOOTER
    )
    OUTPUT.write_text(content, encoding="utf-8")
    print(f"Wrote {len(lines)} entries to {OUTPUT.relative_to(REPO_ROOT)}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())


