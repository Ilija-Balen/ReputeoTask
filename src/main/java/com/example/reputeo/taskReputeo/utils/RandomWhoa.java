package com.example.reputeo.taskReputeo.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RandomWhoa {
    private String movie;
    private int year;
    private String director;
    private String character;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("movie_duration")
    private LocalTime movieDuration;

    private LocalTime timestamp;

    @JsonProperty("full_line")
    private String fullLine;

    @JsonProperty("current_whoa_in_movie")
    private int currentWhoaInMovie;

    @JsonProperty("total_whoas_in_movie")
    private int totalWhoasInMovie;

    @JsonProperty("whoa_grouping")
    private WhoaGrouping whoaGrouping;
    private String poster;
    private String audio;
    private Video video;

    @Override
    public String toString() {
        return "RandomWhoa{" +
                "movie='" + movie + '\'' +
                ", year=" + year +
                ", releaseDate=" + releaseDate +
                ", director='" + director + '\'' +
                ", character='" + character + '\'' +
                ", movieDuration=" + movieDuration +
                ", timestamp=" + timestamp +
                ", fullLine='" + fullLine + '\'' +
                ", currentWhoaInMovie=" + currentWhoaInMovie +
                ", totalWhoasInMovie=" + totalWhoasInMovie +
                ", whoaGrouping=" + (whoaGrouping != null ? whoaGrouping.toString() : "null") +
                ", poster='" + poster + '\'' +
                ", audio='" + audio + '\'' +
                ", video=" + (video != null ? video.toString() : "null") +
                '}';
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WhoaGrouping{
        @JsonProperty("movie_whoa_group_index")
        private int movieWhoaGroupIndex;
        @JsonProperty("current_whoa_in_group")
        private int currentWhoaInGroup;
        @JsonProperty("total_whoas_in_group")
        private int totalWhoasInGroup;

        @Override
        public String toString() {
            return "WhoaGrouping{" +
                    "movieWhoaGroupIndex=" + movieWhoaGroupIndex +
                    ", currentWhoaInGroup=" + currentWhoaInGroup +
                    ", totalWhoasInGroup=" + totalWhoasInGroup +
                    '}';
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Video {
        @JsonProperty("1080p")
        private String p1080;
        @JsonProperty("720p")
        private String p720;
        @JsonProperty("480p")
        private String p480;
        @JsonProperty("360p")
        private String p360;

        @Override
        public String toString() {
            return "Video{" +
                    "1080p='" + p1080 + '\'' +
                    ", 720p='" + p720 + '\'' +
                    ", 480p='" + p480 + '\'' +
                    ", 360p='" + p360 + '\'' +
                    '}';
        }
    }
}
