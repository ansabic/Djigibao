package com.mite.djigibao.domain.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.mite.djigibao.core.FIRESTORE_SONGS
import com.mite.djigibao.core.OFFSET_DATE_TIME_PATTERN
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.User
import com.mite.djigibao.model.Role
import com.mite.djigibao.repository.songs.ISongsRepository
import com.mite.djigibao.repository.users.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.resume

class FirestoreSongUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firestoreUserUseCase: FirestoreUserUseCase,
    private val songRepository: ISongsRepository,
    private val userRepository: IUserRepository
) {
    private suspend fun getSongs() = withContext(Dispatchers.IO) {
        firestoreUserUseCase.syncUsers()
        val localSongs = songRepository.getAllSongs()
        val users = userRepository.getAllUsers()
        return@withContext suspendCancellableCoroutine<List<Song>> { continuation ->
            val document = firestore
                .collection(FIRESTORE_SONGS)
                .get()
                .addOnSuccessListener {
                val data = it.documents
                val resultList = mutableListOf<Song>()
                data.forEach { document ->
                    val fireSong = document.toObject(FirestoreSong::class.java)
                    if (!localSongs.map { song ->
                            song.name
                        }.contains(fireSong?.name))
                        fireSong?.let { firestoreSong ->
                            mapFirestoreSongToSong(
                                firestoreSong,
                                users
                            )
                        }?.let { song ->
                            resultList.add(
                                song
                            )
                        }
                }
                continuation.resume(resultList.toList())


            }
            document.addOnFailureListener {
                continuation.cancel()
            }
            document.addOnCanceledListener {
                continuation.cancel()
            }
        }
    }

    suspend fun addSong(song: Song) = withContext(Dispatchers.IO) {
        val remote = getSongs()
        return@withContext if (!remote.map { it.name }
                .contains(song.name)) {
            suspendCancellableCoroutine { continuation ->
                firestore
                    .collection(FIRESTORE_SONGS)
                    .document(song.name)
                    .set(mapSongToFirestoreSong(song))
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        continuation.cancel()
                    }
                    .addOnCanceledListener {
                        continuation.cancel()
                    }
            }
        } else
            false

    }
    private fun mapSongToFirestoreSong(song: Song): FirestoreSong {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(OFFSET_DATE_TIME_PATTERN).withZone(
            ZoneId.of("Europe/Berlin"))
        return FirestoreSong(
            song.name,song.body,
            song.duration,
            song.creationDate.format(dateTimeFormatter),
            song.instruments,
            song.user.username,
            song.songLocationLocal,
            song.songLocationRemote,
        )
    }

    private fun mapFirestoreSongToSong(remote: FirestoreSong, users: List<User>?): Song {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(OFFSET_DATE_TIME_PATTERN).withZone(
            ZoneId.of("Europe/Berlin"))
        return Song(
            remote.name,
            remote.body,
            remote.duration,
            ZonedDateTime.parse(remote.creationDate, dateTimeFormatter),
            remote.instruments,
            users?.find { remote.user == it.username } ?: User("", Role.NONE),
            remote.songLocationLocal,
            remote.songLocationRemote
        )
    }
}