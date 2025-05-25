use EduBridge_database


CREATE TABLE Tag (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE [Language] (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE [user] (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(100) NOT NULL UNIQUE,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    role NVARCHAR(20) CHECK (role IN ('instructor', 'learner','admin')) NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE Instructor (
    id BIGINT PRIMARY KEY, -- là FK đến user
    bio NVARCHAR(MAX),
    CONSTRAINT FK_Instructor_User FOREIGN KEY (id) REFERENCES [user](id) ON DELETE CASCADE
);

CREATE TABLE Learner (
    id BIGINT PRIMARY KEY,
    -- có thể thêm các cột bổ sung sau này, như: level, preferences, etc.
    CONSTRAINT FK_Learner_User FOREIGN KEY (id) REFERENCES [user](id) ON DELETE CASCADE
);

ALTER TABLE Course
ADD last_update DATETIME;

CREATE TABLE Course (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(255) NOT NULL,
    headline NVARCHAR(255),
    [description] NVARCHAR(MAX),
    requirement NVARCHAR(MAX),
    course_for NVARCHAR(MAX),
    learning_outcomes NVARCHAR(MAX),
    language_id BIGINT,
    url_thumbnail NVARCHAR(500),
    is_paid BIT NOT NULL DEFAULT 0,
    status NVARCHAR(20) NOT NULL CHECK (status IN ('draft', 'public', 'archive')),
    published_time DATETIME,
	last_update DATETIME,--xiu nua chay lai dong nay
	CONSTRAINT FK_Language_Tag FOREIGN KEY (language_id) REFERENCES [Language](id)
);

CREATE TABLE Pricing (
    course_id BIGINT PRIMARY KEY,
    price DECIMAL(10,2) NOT NULL,
    sale DECIMAL(5,2),
    VAT DECIMAL(5,2),
    currency VARCHAR(10) DEFAULT 'VND',
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME,

    CONSTRAINT FK_Pricing_Course FOREIGN KEY (course_id)
        REFERENCES Course(id)
        ON DELETE CASCADE
);


CREATE TABLE Course_Tag (
    course_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, tag_id),
    CONSTRAINT FK_CourseTag_Course FOREIGN KEY (course_id)
        REFERENCES Course(id) ON DELETE CASCADE,
    CONSTRAINT FK_CourseTag_Tag FOREIGN KEY (tag_id)
        REFERENCES Tag(id) ON DELETE CASCADE
);


CREATE TABLE Course_Instructor (
    course_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
	    created DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (course_id, instructor_id),
    CONSTRAINT FK_CourseInstructor_Course FOREIGN KEY (course_id)
        REFERENCES Course(id) ON DELETE CASCADE,
    CONSTRAINT FK_CourseInstructor_Instructor FOREIGN KEY (instructor_id)
        REFERENCES Instructor(id) ON DELETE CASCADE
);

-- LANGUAGE
INSERT INTO [Language] (name) VALUES
(N'English'),
(N'Vietnamese'),
(N'Japanese');

-- TAG
INSERT INTO Tag (name) VALUES
(N'Web Development'),
(N'Data Science'),
(N'Graphic Design');

-- USER
INSERT INTO [user] (username, email, password, role) VALUES
(N'Nguyen Luong Hieu Thuan', N'thuan@example.com', N'1', N'instructor'),
(N'Nguyen Tran Thanh Duy', N'duy@example.com', N'1', N'instructor'),
(N'admin_user', N'admin@example.com', N'adminpass', N'admin');

-- INSTRUCTOR (liên kết user id = 1)
INSERT INTO Instructor (id, bio) VALUES
(1, N'Experienced web developer with 10 years in the industry');

-- LEARNER (liên kết user id = 2)
INSERT INTO Learner (id) VALUES
(2);

-- COURSE
INSERT INTO Course (title, headline, [description], requirement, course_for, learning_outcomes, language_id, url_thumbnail, is_paid, status, published_time) VALUES
(N'HTML for Beginners', N'Learn the basics of HTML', N'Full course to learn HTML.', N'Computer with internet', N'Beginners', N'HTML5 structure', 1, N'https://www.fahasa.com/blog/wp-content/uploads/2025/02/thelastattackmovie_1.webp', 1, N'public', GETDATE()),
(N'Python for Data Analysis', N'Analyze data with Python', N'Includes pandas, numpy, matplotlib.', N'Basic Python', N'Analysts, Data enthusiasts', N'Data cleaning, visualization', 1, N'https://www.fahasa.com/blog/wp-content/uploads/2025/02/thelastattackmovie_1.webp', 1, N'public', GETDATE()),
(N'Photoshop Mastery', N'Master Photoshop from scratch', N'Design beautiful graphics.', N'Photoshop installed', N'Graphic designers', N'Layering, Effects, Tools', 2, N'https://www.fahasa.com/blog/wp-content/uploads/2025/02/thelastattackmovie_1.webp', 0, N'draft', NULL);

-- PRICING
INSERT INTO Pricing (course_id, price, sale, VAT, currency, updated_at) VALUES
(1, 500000, 10, 5, 'VND', GETDATE()),
(2, 800000, 20, 10, 'VND', GETDATE()),
(3, 0, NULL, NULL, 'VND', NULL);  -- Free course

-- COURSE_TAG
INSERT INTO Course_Tag (course_id, tag_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- COURSE_INSTRUCTOR
INSERT INTO Course_Instructor (course_id, instructor_id) VALUES
(1, 1),
(2, 1),
(3, 1);
use Edubridge_database
select * from Course






