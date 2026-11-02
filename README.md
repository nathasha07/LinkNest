# 🍂 LinkNest

**A cozy place for everything worth saving.**

LinkNest is an Android bookmark manager designed to help users save, organize, search and manage their favorite links in one place. With a warm autumn-inspired interface, customizable themes and collection-based organization, LinkNest makes managing saved resources simple and enjoyable.

## 📱 Screenshots

### Home Screen

| Light Mode | Dark Mode |
|:---:|:---:|
| ![Home Light](screenshots/home-light.png) | ![Home Dark](screenshots/home-dark.png) |

### Other Screens

| Collections | Favorites |
|:---:|:---:|
| ![Collections](screenshots/collections.png) | ![Favorites](screenshots/favorites.png) |

| Settings | Add Bookmark |
|:---:|:---:|
| ![Settings](screenshots/settings.png) | ![Add Bookmark](screenshots/add-bookmark.png) |

## ✨ Features

- **Bookmark Management** – Save, edit, delete and organize website links.
- **Collections** – Group bookmarks into custom collections.
- **Favorites** – Mark important bookmarks for quick access.
- **Search** – Find saved links using titles, URLs and tags.
- **Sorting** – Organize bookmarks by different sorting options.
- **Share Links** – Copy and share saved URLs.
- **Theme Customization** – Switch between Light, Dark and System themes.
- **Local Storage** – Store bookmarks and collections locally using Room.
- **Backup and Restore** – Export and restore bookmark data.
- **Link Information** – Retrieve webpage titles when available.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Jetpack Compose | Declarative UI development |
| Material 3 | UI components and theming |
| Room Database | Local data persistence |
| DataStore | Saving user preferences |
| Android Studio | Development environment |

## 🏗️ Project Structure

```text
LinkNest/
│
├── app/
│   └── src/main/
│       ├── java/com/example/linknest/
│       │   ├── data/
│       │   ├── database/
│       │   ├── repository/
│       │   ├── viewmodel/
│       │   └── ui/
│       │       ├── screens/
│       │       └── theme/
│       │
│       └── res/
│
├── screenshots/
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- Android Studio
- Android SDK
- Kotlin-compatible Android development environment

### Installation

1. Clone the repository:

```bash
git clone https://github.com/YOUR-USERNAME/LinkNest.git
```

2. Open the project in Android Studio.

3. Allow Gradle Sync to complete.

4. Connect an Android device or start an emulator.

5. Click **Run** to build and launch the application.

## 🎨 Design

LinkNest uses an autumn-inspired color palette to create a warm and comfortable user experience.

- Olive Green – `#8A8635`
- Brick Red – `#AA2B1D`
- Burnt Orange – `#CC561E`
- Golden Wheat – `#F3CF7A`
- Cream – `#FFF8E9`

The application supports both light and dark themes.

## 🔮 Future Improvements

- Advanced bookmark filtering.
- Improved automatic website metadata extraction.
- Additional customization options.
- Further UI and accessibility improvements.

## 👩‍💻 Developer

Developed as a personal Android application project to explore Kotlin, Jetpack Compose, local database management and modern Android development.

**LinkNest – Keep your favorite links close.** 🍂
