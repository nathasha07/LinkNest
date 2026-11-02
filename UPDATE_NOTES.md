# LinkNest integrated update

This update extends the existing Kotlin + Jetpack Compose project without changing its package name or autumn color palette.

## Included changes

- Added edit mode to the existing Add Bookmark screen. Existing title, URL, description, category, tags, favorite state, collection assignment and creation timestamp are retained/updated appropriately.
- Added duplicate-URL validation when editing a bookmark.
- Added Copy URL and Android Share actions to bookmark cards.
- Added sorting on Home: newest, oldest, title A–Z and title Z–A.
- Expanded Home search to include description and category as well as title, URL and tags.
- Added collection rename/edit support.
- Added optional webpage title fetching from the entered URL. This can fail on sites that block requests; manual title entry remains available.
- Added persistent System / Light / Dark theme selection using the existing DataStore dependency.
- Added JSON backup export and restore for bookmarks and collections. Restore replaces the current local bookmark and collection data; the UI warns about this.
- Replaced destructive Room migration fallback with a 1-to-2 migration that adds collection assignment and creates the collections table.

## Validation note

The source was reviewed and updated, but a fresh Gradle build could not be run in this environment because the Gradle 9.2.1 distribution was not cached and network access to services.gradle.org was unavailable. Open the project in Android Studio, sync Gradle, and run **Build > Make Project** before installing it. Resolve any environment-specific Gradle or SDK errors shown there before using the updated APK.

\n## Theme and sort-menu readability fix
- Replaced hard-coded light-palette colors in Compose screens and MainActivity with MaterialTheme.colorScheme roles.
- Light and dark modes now use different backgrounds, surfaces, text, borders, and accent colors throughout the UI.
- Set the sort DropdownMenu surface and option text colors explicitly from the active Material 3 color scheme.
- Highlighted the currently selected sort option.
