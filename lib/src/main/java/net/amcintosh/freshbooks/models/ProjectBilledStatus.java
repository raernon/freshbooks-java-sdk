package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

/**
 * Billing statuses for a project, computed from invoice totals that have been sent
 * for that project.
 */
public enum ProjectBilledStatus {
    @Value billed,
    @Value partially_billed,
    @Value unbilled
}