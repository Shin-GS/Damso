# 담소(Damso)

**구독 플랫폼 담소**

---

## 📄 Notion 문서

[담소 프로젝트 Notion 바로가기](https://www.notion.so/150d484dae8180f882a5e2f25d5d0c6d)

---

## 🛠 기술 스택

- **Backend**
    - Java
    - Spring Boot
    - Spring JPA
    - Spring QueryDSL
    - Spring OAuth2
- **Frontend**
    - Thymeleaf
    - Vanilla JavaScript
    - HTMX
    - Tailwind CSS
- **Database & Caching**
    - MySQL
    - Redis

---

## 🔧 설치 프로그램

- **MySQL**
- **Redis**
- **Node.js**

---

## 🔑 환경 변수 설정

### Java 환경 변수 설정 (Windows 기준)

1. **Java 다운로드**
    - [Java JDK 다운로드](https://www.oracle.com/java/technologies/javase-downloads.html)
2. **설치 후 환경 변수 설정**
    - 시스템 속성 → 고급 → 환경 변수
    - **시스템 변수**에서 `새로 만들기` 클릭
        - **변수 이름**: `JAVA_HOME`
        - **변수 값**: `C:\Program Files\Java\jdk-XX.X.X`
    - `Path` 변수 편집 → `C:\Program Files\Java\jdk-XX.X.X\bin` 추가
3. **확인**
   ```bash
   java -version
   javac -version
   ```

### Node.js 및 npm 환경 변수 설정

1. **Node.js 다운로드 및 설치**
    - [Node.js 다운로드](https://nodejs.org/)
2. **설치 후 환경 변수 확인**
    - 터미널에서 다음 명령어 실행:
      ```bash
      node -v
      npm -v
      ```
3. **환경 변수 추가 (필요한 경우)**
    - 시스템 속성 → 고급 → 환경 변수
    - **시스템 변수**에서 `Path` 편집 → `C:\Program Files\nodejs` 추가

---

## 🧩 Intellij IDEA Tailwind CSS 설정

1. **File**\
   → **Settings** (또는 **Preferences** on Mac)
2. **Build, Execution, Deployment**\
   → **Build Tools**\
   → **Gradle**
3. **Run Configurations**
    - **Build and run using**: Gradle
    - **Run tests using**: Gradle

---

## 🎨 Tailwind CSS 빌드 및 output.css 파일 생성

### Tailwind CSS 자동 생성 (Gradle Task 연동)

Tailwind CSS 빌드는 build.gradle에 자동화되어 있으며, `bootRun` 또는 `bootJar`를 실행하면 output.css가 자동으로 생성됩니다.

```gradle
// Tailwind 빌드 Task 추가 (OS에 따라 npm 경로 자동 설정)
tasks.register('tailwindBuild') {
    group = "build"
    description = "Tailwind CSS 빌드 실행"
    doLast {
        println "Tailwind CSS 빌드를 시작합니다..."
        def npmCommand = System.getProperty('os.name').toLowerCase().contains('win') ? 'npm.cmd' : 'npm'
        def process = new ProcessBuilder(npmCommand, 'run', 'build')
                .directory(projectDir)
                .inheritIO()
                .start()
        def exitCode = process.waitFor()
        if (exitCode != 0) {
            throw new GradleException("Tailwind CSS 빌드 실패!")
        }
        println "Tailwind CSS 빌드 완료 (npm run build)"
    }
}

// 모든 bootRun 및 bootJar Task에 Tailwind 빌드 의존성 추가
tasks.withType(BootRun).configureEach {
    dependsOn tailwindBuild
}

tasks.withType(BootJar).configureEach {
    dependsOn tailwindBuild
}
```

---

## 📥 CDN 파일 다운로드 및 저장

### CDN 파일 자동 다운로드 (Gradle Task 연동)

admin 및 user 모듈에서 `bootRun` 또는 `bootJar`를 실행하면 필요한 CDN 파일이 자동으로 다운로드 및 저장됩니다.

```gradle
def cdnFiles = [
        [url: "https://cdn.quilljs.com/1.3.7/quill.snow.css", dest: "src/main/resources/static/css/quill.snow.css"],
        [url: "https://cdn.quilljs.com/1.3.7/quill.min.js", dest: "src/main/resources/static/script/quill.min.js"],
        [url: "https://unpkg.com/htmx.org@2.0.4/dist/htmx.min.js", dest: "src/main/resources/static/script/htmx.min.js"],
        [url: "https://unpkg.com/htmx-ext-json-enc@2.0.0/json-enc.js", dest: "src/main/resources/static/script/json-enc.js"]
]
```

---

더 자세한 설정이나 문의는 [Notion 문서](https://www.notion.so/150d484dae8180f882a5e2f25d5d0c6d)를 참고하세요!

