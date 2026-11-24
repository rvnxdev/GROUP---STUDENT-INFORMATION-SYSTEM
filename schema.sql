CREATE TABLE IF NOT EXISTS students (
                                        id INTEGER PRIMARY KEY,
                                        name TEXT NOT NULL,
                                        age INTEGER,
                                        course TEXT,
                                        year_level INTEGER,
                                        gpa REAL
);

CREATE TABLE IF NOT EXISTS instructors (
                                           id INTEGER PRIMARY KEY,
                                           name TEXT NOT NULL,
                                           age INTEGER,
                                           department TEXT
);