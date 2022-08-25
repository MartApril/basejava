package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import javax.management.monitor.Monitor;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

public class Organization extends OrganizationSection implements Serializable{
    private static final long serialVersionUID = 1L;
    private String title;
    private String website;
    private List<Period> periods;

    public Organization(List<Organization> organizations) {
        super(organizations);
    }

    public Organization(String title, String website, List<Period> periods) {
        Objects.requireNonNull(title, "title must not be null");
        this.title = title;
        this.website = website;
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!title.equals(that.title)) return false;
        if (!website.equals(that.website)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + website.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return '\'' + title + '\'' +
                ", '" + website + '\'' +
                ", " + periods;
    }

    public static class Period implements Serializable {
        private final String title;
        private final LocalDate start;
        private final LocalDate end;
        private final String description;

        public Period(String title, LocalDate start, LocalDate end, String description) {
            Objects.requireNonNull(start, "start must not be null");
            Objects.requireNonNull(end, "end must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.title = title;
            this.start = start;
            this.end = end;
            this.description = description;
        }
        public Period(String title,int startYear, Month startMonth,  String description) {
            this(title, of(startYear, startMonth), NOW, description);
        }

        public Period(String title,int startYear, Month startMonth, int endYear, Month endMonth, String description) {
            this(title, of(startYear, startMonth),of(endYear, endMonth), description);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!title.equals(period.title)) return false;
            if (!start.equals(period.start)) return false;
            if (!end.equals(period.end)) return false;
            return description.equals(period.description);
        }

        @Override
        public int hashCode() {
            int result = title.hashCode();
            result = 31 * result + start.hashCode();
            result = 31 * result + end.hashCode();
            result = 31 * result + description.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return title +
                    " c " + start +
                    " по " + end + " - " +
                    description;
        }
    }
}
