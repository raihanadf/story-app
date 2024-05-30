package com.raihan.story.utils

import com.raihan.story.data.model.dto.story.Story

object DataDummy {

    fun generateStory(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                id = i.toString(),
                name = "Story #$i",
                description = "Description: #$i",
                photoUrl = "https://picsum.photos/600/400?random=$i",
                createdAt = "2024-09-17T00:00:00Z",
                lat = (-1.17),
                lon = 117.09
            )
            items.add(story)
        }
        return items
    }
}
