package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    List<Organization> organizations;

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations != null ? organizations.equals(that.organizations) : that.organizations == null;
    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }

    @Override
    public String toString() {
        return organizations.stream().map(Object::toString)
                .collect(Collectors.joining("\n "));
    }

    @Override
    public String getContentAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organization string : organizations) {
            stringBuilder.append(string).append("\n");
        }
        return stringBuilder.toString();
    }
}
