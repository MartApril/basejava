package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.List;

public class Organization extends OrganizationSection {
    String title;
    String website;
    Period periods;

    public Organization(List<Organization> organizations) {
        super(organizations);
    }

    public Organization(String title, String website, Period periods) {
        this.title = title;
        this.website = website;
        this.periods = periods;
    }

    @Override
    public String toString() {
        return '\'' + title + '\'' +
                ", '" + website + '\'' +
                ", " + periods;
    }

    public static class Period {
        private final String title;
        private final LocalDate start;
        private final LocalDate end;
        private final String description;

        @Override
        public String toString() {
            return title +
                    " c " + start +
                    " по " + end + " - " +
                    description;
        }

        public Period(String title, LocalDate start, LocalDate end, String description) {
            this.title = title;
            this.start = start;
            this.end = end;
            this.description = description;
        }
    }
}
