package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection{
    private static final long serialVersionUID = 1L;
    List<Organization> organizations;

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
