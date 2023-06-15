package com.renju.albums.data.mapper

import com.renju.albums.data.entity.*
import com.renju.albums.data.local.MusicLocalEntity
import com.renju.albums.domain.model.MusicDetails
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MusicDetailsResponseMapperTest {
    @InjectMocks
    private lateinit var subject: MusicDetailsResponseMapper

    @Test
    fun mapToMusicDetailsFromNetwork_givenMusicEntity_returnsListOfMusicDetails() {
        val actual = subject.mapToMusicDetailsFromNetwork(buildMusicEntity())

        assertThat(actual.size).isEqualTo(3)

        assertThat(actual[0])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel"
            ).containsExactly(
                "name1",
                "label3",
                "title1",
                "price1",
                "artist1"
            )

        assertThat(actual[1])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel"
            ).containsExactly(
                "name2",
                "label3",
                "title2",
                "price2",
                "artist2"
            )

        assertThat(actual[2])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel"
            ).containsExactly(
                "name3",
                "label3",
                "title3",
                "price3",
                "artist3"
            )

    }

    @Test
    fun mapToMusicDetailsFromLocal_givenMusicLocalEntityList_returnsListOfMusicDetails() {
        val actual = subject.mapToMusicDetailsFromLocal(buildMusicLocalEntity())

        assertThat(actual.size).isEqualTo(3)

        assertThat(actual[0])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel"
            ).containsExactly(
                "name1",
                "label1",
                "title1",
                "price1",
                "artist1"
            )

        assertThat(actual[1])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel"
            ).containsExactly(
                "name2",
                "label2",
                "title2",
                "price2",
                "artist2"
            )

        assertThat(actual[2])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel"
            ).containsExactly(
                "name3",
                "label3",
                "title3",
                "price3",
                "artist3"
            )

    }

    @Test
    fun mapToLocalEntityFromMusicDetails_givenMusicDetailsList_returnsListOfMusicLocalEntity() {
        val actual = subject.mapToLocalEntityFromMusicDetails(buildMusicDetail())

        assertThat(actual.size).isEqualTo(3)

        assertThat(actual[0])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel",
                "musicLink"
            ).containsExactly(
                "name1",
                "label1",
                "title1",
                "price1",
                "artist1",
                "link1"
            )

        assertThat(actual[1])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel",
                "musicLink"
            ).containsExactly(
                "name2",
                "label2",
                "title2",
                "price2",
                "artist2",
                "link2"
            )

        assertThat(actual[2])
            .extracting(
                "musicName",
                "musicImageUrl",
                "musicTitle",
                "musicPriceLabel",
                "artistLabel",
                "musicLink"
            ).containsExactly(
                "name3",
                "label3",
                "title3",
                "price3",
                "artist3",
                "link3"
            )

    }

    private fun buildMusicLocalEntity(): List<MusicLocalEntity> {
        return listOf(
            MusicLocalEntity(
                "name1",
                "label1",
                "title1",
                "price1",
                "artist1"
            ),
            MusicLocalEntity(
                "name2",
                "label2",
                "title2",
                "price2",
                "artist2"
            ),
            MusicLocalEntity(
                "name3",
                "label3",
                "title3",
                "price3",
                "artist3"
            )
        )

    }

    private fun buildMusicDetail(): List<MusicDetails> {
        return listOf(
            MusicDetails(
                "name1",
                "label1",
                "title1",
                "price1",
                "artist1",
                "link1"
            ),
            MusicDetails(
                "name2",
                "label2",
                "title2",
                "price2",
                "artist2",
                "link2"
            ),
            MusicDetails(
                "name3",
                "label3",
                "title3",
                "price3",
                "artist3",
                "link3"
            )
        )

    }

    private fun buildMusicEntity(): MusicEntity {
        return MusicEntity(
            Feed(
                null,
                null,
                listOf(
                    Entry(
                        ImName("name1"),
                        listOf(
                            ImImage("label1", buildAttributes()),
                            ImImage("label2", buildAttributes()),
                            ImImage("label3", buildAttributes())
                        ),
                        ImItemCount("count1"),
                        ImPrice("price1", buildAttributes()),
                        ImContentType("content1", buildAttributes()),
                        MusicTitle("title1"),
                        MusicLink(buildAttributes()),
                        Category(buildAttributes()),
                        ImReleaseDate("release1", buildAttributes()),
                        ImArtist("artist1", buildAttributes())
                    ),
                    Entry(
                        ImName("name2"),
                        listOf(
                            ImImage("label1", buildAttributes()),
                            ImImage("label2", buildAttributes()),
                            ImImage("label3", buildAttributes())
                        ),
                        ImItemCount("count2"),
                        ImPrice("price2", buildAttributes()),
                        ImContentType("content2", buildAttributes()),
                        MusicTitle("title2"),
                        MusicLink(buildAttributes()),
                        Category(buildAttributes()),
                        ImReleaseDate("release2", buildAttributes()),
                        ImArtist("artist2", buildAttributes())
                    ),
                    Entry(
                        ImName("name3"),
                        listOf(
                            ImImage("label1", buildAttributes()),
                            ImImage("label2", buildAttributes()),
                            ImImage("label3", buildAttributes())
                        ),
                        ImItemCount("count3"),
                        ImPrice("price3", buildAttributes()),
                        ImContentType("content3", buildAttributes()),
                        MusicTitle("title3"),
                        MusicLink(buildAttributes()),
                        Category(buildAttributes()),
                        ImReleaseDate("release3", buildAttributes()),
                        ImArtist("artist3", buildAttributes())
                    ),
                ),
                null
            )
        )
    }

    private fun buildAttributes(): Attributes {
        return Attributes(
            "height",
            "rel",
            "type",
            "href",
            "amount",
            "currency",
            "term",
            "label",
            "imid",
            "scheme"
        )
    }
}