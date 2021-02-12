package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.ProjectList;
import net.amcintosh.freshbooks.resources.Projects;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectResourceTest {

    @Test
    public void getResource_notFound() throws IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_project_response__not_found.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(404, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321", null)).thenReturn(mockRequest);

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);

        try {
            projects.get(439000, projectId);
        } catch (FreshBooksException e) {
            assertEquals(404, e.statusCode);
            assertEquals("Requested resource could not be found.", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
    }

    @Test
    public void getResource_badResponse() throws IOException {
        String jsonResponse = "stuff";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(500, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321", null)).thenReturn(mockRequest);

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);

        try {
            projects.get(439000, projectId);
        } catch (FreshBooksException e) {
            assertEquals(500, e.statusCode);
            assertEquals("Returned an unexpected response", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
    }

    @Test
    public void getResource_missingResponse() throws IOException {
        String jsonResponse = "{\"foo\": \"bar\"}";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321", null)).thenReturn(mockRequest);

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);

        try {
            projects.get(439000, projectId);
        } catch (FreshBooksException e) {
            assertEquals(200, e.statusCode);
            assertEquals("Returned an unexpected response", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
    }

    @Test
    public void listResource_noMatches() throws FreshBooksException, IOException {
        String jsonResponse = "{\"meta\": {\"sort\": [], \"total\": 0, \"per_page\": 1, " +
                "\"page\": 1, \"pages\": 0}, \"projects\": []}";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects")).thenReturn(mockRequest);

        Projects projects = new Projects(mockedFreshBooksClient);
        ProjectList projectList = projects.list(439000);


        assertEquals(0, projectList.getPages().getTotal());
        assertEquals(ImmutableList.of(), projectList.getProjects());
    }

}