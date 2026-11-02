
package com.example.linknest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linknest.data.Collection
import com.example.linknest.ui.theme.*
import com.example.linknest.viewmodel.BookmarkViewModel

@Composable
fun CollectionsScreen(
    onCollectionClick: (Collection) -> Unit = {},
    viewModel: BookmarkViewModel = viewModel()
) {
    val collections by viewModel.collections.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var collectionName by remember { mutableStateOf("") }
    var collectionDescription by remember { mutableStateOf("") }
    var editingCollection by remember { mutableStateOf<Collection?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        // HEADER
        Text(
            text = "My Collections",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Organize your saved links your way.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // CREATE COLLECTION BUTTON
        Button(
            onClick = {
                editingCollection = null
                collectionName = ""
                collectionDescription = ""
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "+",
                fontSize = 22.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Create Collection",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // COLLECTION LIST
        if (collections.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📁",
                        style = MaterialTheme.typography.displayMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "No collections yet",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Create your first collection to get started.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = collections,
                    key = { it.id }
                ) { collection ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCollectionClick(collection)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📂",
                                style = MaterialTheme.typography.headlineMedium
                            )

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = collection.name,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                if (collection.description.isNotBlank()) {
                                    Text(
                                        text = collection.description,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                TextButton(
                                    onClick = {
                                        editingCollection = collection
                                        collectionName = collection.name
                                        collectionDescription = collection.description
                                        showDialog = true
                                    }
                                ) {
                                    Text("Edit", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                                }
                                TextButton(
                                    onClick = { viewModel.deleteCollection(collection) }
                                ) {
                                    Text("Delete", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // CREATE COLLECTION DIALOG
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                collectionName = ""
                collectionDescription = ""
            },

            title = {
                Text(
                    text = if (editingCollection == null) "New Collection" else "Edit Collection",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Column {
                    OutlinedTextField(
                        value = collectionName,
                        onValueChange = {
                            collectionName = it
                        },
                        label = {
                            Text(
                                "Collection name",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = collectionDescription,
                        onValueChange = {
                            collectionDescription = it
                        },
                        label = {
                            Text(
                                "Description (optional)",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        val name = collectionName.trim()

                        if (name.isNotEmpty()) {
                            val description = collectionDescription.trim()
                            val current = editingCollection
                            if (current == null) {
                                viewModel.addCollection(
                                    Collection(name = name, description = description)
                                )
                            } else {
                                viewModel.renameCollection(
                                    current.copy(name = name, description = description)
                                )
                            }

                            showDialog = false
                            editingCollection = null
                            collectionName = ""
                            collectionDescription = ""
                        }
                    }
                ) {
                    Text(
                        text = if (editingCollection == null) "Create" else "Save changes",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        editingCollection = null
                        collectionName = ""
                        collectionDescription = ""
                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },

            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}
