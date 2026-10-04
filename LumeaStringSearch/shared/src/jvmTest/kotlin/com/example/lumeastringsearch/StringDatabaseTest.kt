package com.example.lumeastringsearch

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.lumeastringsearch.data.local.StringDatabase
import com.example.lumeastringsearch.data.local.StringSeedData
import com.example.lumeastringsearch.data.local.StringSeeder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * End-to-end check that the Excel-derived seed data really lands in SQLite and that
 * the platform filter returns the right rows.
 *
 * Uses a throwaway temp file rather than the real ~/.lumea location.
 */
class StringDatabaseTest {

    private lateinit var dbFile: File
    private lateinit var db: StringDatabase

    @BeforeTest
    fun setUp() {
        dbFile = File.createTempFile("lumea-test-", ".db").also { it.delete() }
        db = Room.databaseBuilder<StringDatabase>(name = dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    @AfterTest
    fun tearDown() {
        db.close()
        dbFile.delete()
        File("${dbFile.absolutePath}-wal").delete()
        File("${dbFile.absolutePath}-shm").delete()
    }

    @Test
    fun `seeds every row once and is idempotent`() = runBlocking {
        val dao = db.stringDao()
        val seeder = StringSeeder(dao)

        assertEquals(0, dao.count(), "fresh database should be empty")

        val inserted = seeder.seedIfEmpty()
        assertEquals(StringSeedData.initialStrings.size, inserted)
        assertEquals(1389, dao.count())

        // Second launch must not duplicate or re-insert.
        assertEquals(0, seeder.seedIfEmpty())
        assertEquals(1389, dao.count())
    }

    @Test
    fun `All returns the union of both platforms, not the intersection`() = runBlocking {
        val dao = db.stringDao()
        StringSeeder(dao).seedIfEmpty()

        val all = dao.searchStringsFiltered("", includeAndroid = true, includeIos = true).first()
        val android = dao.searchStringsFiltered("", includeAndroid = true, includeIos = false).first()
        val ios = dao.searchStringsFiltered("", includeAndroid = false, includeIos = true).first()

        // 1235 rows are on both platforms, 154 are iOS-exclusive.
        // The old query ANDed the two flags and silently dropped those 154.
        assertEquals(1389, all.size, "All must not drop platform-exclusive strings")
        assertEquals(1235, android.size)
        assertEquals(1389, ios.size)
    }

    @Test
    fun `preserves excel row numbers and orders by them`() = runBlocking {
        val dao = db.stringDao()
        StringSeeder(dao).seedIfEmpty()

        val rows = dao.searchStringsFiltered("", includeAndroid = true, includeIos = true).first()

        assertEquals(3, rows.first().rowNumber, "first spreadsheet data row is 3")
        assertEquals("lumea_bodyarea_leg", rows.first().key)
        assertTrue(rows.zipWithNext().all { (a, b) -> a.rowNumber <= b.rowNumber }, "must be row-ordered")

        val single = dao.getStringByKey("lumea_bodyarea_leg")
        assertEquals("Legs", single?.value)
        assertEquals(3, single?.rowNumber)
    }

    @Test
    fun `search matches key and value`() = runBlocking {
        val dao = db.stringDao()
        StringSeeder(dao).seedIfEmpty()

        val byValue = dao.searchStringsFiltered("Bikini", includeAndroid = true, includeIos = true).first()
        assertTrue(byValue.any { it.key == "lumea_bodyarea_bikini_line" })

        val byKey = dao.searchStringsFiltered("bodyarea", includeAndroid = true, includeIos = true).first()
        assertTrue(byKey.size >= 10)
    }
}

