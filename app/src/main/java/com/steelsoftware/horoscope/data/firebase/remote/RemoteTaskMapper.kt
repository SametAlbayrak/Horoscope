package com.steelsoftware.horoscope.data.firebase.remote

import com.steelsoftware.horoscope.data.firebase.Task
import lav.cloudfirestoreexample.data.remote.RemoteTask

/**
 * Created by Anatoliy Lukyanov on 10/03/2019.
 *
 */
fun mapToTask(remoteTask: RemoteTask): Task {
    return Task(
            remoteTask.id,
            remoteTask.title
    )
}

fun mapToRemoteTask(task: Task): RemoteTask {
    return RemoteTask(
            task.id,
            task.title
    )
}