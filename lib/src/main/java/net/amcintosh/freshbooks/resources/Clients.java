package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.ClientList;
import net.amcintosh.freshbooks.models.VisState;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;
import net.amcintosh.freshbooks.models.api.AccountingResponse;
import net.amcintosh.freshbooks.resources.api.AccountingResource;

import java.util.Map;

/**
 * FreshBooks clients resource with calls to get, list, create, update, delete
 */
public class Clients extends AccountingResource {

    public Clients(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPath() {
        return "users/clients";
    }


    public ClientList list(String accountId) throws FreshBooksException {
        String url = this.getUrl(accountId);
        AccountingListResponse result = this.handleListRequest(url);
        return new ClientList(result.response.result);
    }

    /**
     * Get a single client with the corresponding id.
     *
     * @param accountId The alpha-numeric account id
     * @param clientId Id of the resource to return
     * //@param builder (Optional) IncludesBuilder object for including additional data, sub-resources, etc.
     *
     * @return The Client
     * @throws FreshBooksException If the call is not successful
     */
    public Client get(String accountId, long clientId) throws FreshBooksException {
        String url = this.getUrl(accountId, clientId);
        AccountingResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.response.result.client;
    }

    /**
     * Create a new client from the provided client model.
     * Makes a POST call against the client resource endpoint.
     *
     * This calls `client.getContent()` to get a hash map of data.
     *
     * @param accountId The alpha-numeric account id
     * @param data Client model with create data
     *
     * @return The created Client
     * @throws FreshBooksException If the call is not successful
     */
    public Client create(String accountId, Client data) throws FreshBooksException {
        return this.create(accountId, data.getContent());
    }

    /**
     * Create a new client from the provided data.
     * Makes a POST call against the client resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param data Map of create data
     *
     * @return The created Client
     * @throws FreshBooksException If the call is not successful
     */
    public Client create(String accountId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(accountId);
        ImmutableMap<String, Object> content = ImmutableMap.of("client", data);
        AccountingResponse result = this.handleRequest(HttpMethods.POST, url, content);
        return result.response.result.client;
    }

    /**
     * Update the client with the corresponding id.
     * Makes a PUT call against the client resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param clientId Id of the resource to return
     * @param data Client model with updated data
     *
     * @return The updated Client
     * @throws FreshBooksException If the call is not successful
     */
    public Client update(String accountId, long clientId, Client data) throws FreshBooksException {
        return this.update(accountId, clientId, data.getContent());
    }

    /**
     * Update the client with the corresponding id.
     * Makes a PUT call against the client resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param clientId Id of the resource to return
     * @param data Map of data to change
     *
     * @return The updated Client
     * @throws FreshBooksException If the call is not successful
     */
    public Client update(String accountId, long clientId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(accountId, clientId);
        ImmutableMap<String, Object> content = ImmutableMap.of("client", data);
        AccountingResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.response.result.client;
    }

    /**
     * Delete the client with the corresponding id.
     * Makes a PUT call against the client resource endpoint with a <pre>vis_state</pre> of deleted.
     *
     * Note: Most FreshBooks resources are soft-deleted,
     * See [FreshBooks API - Active and Deleted Objects](https://www.freshbooks.com/api/active_deleted)
     *
     * @param accountId The alpha-numeric account id
     * @param clientId Id of the resource to return
     *
     * @return The deleted Client
     * @throws FreshBooksException If the call is not successful
     */
    public Client delete(String accountId, long clientId) throws FreshBooksException {
        String url = this.getUrl(accountId, clientId);
        ImmutableMap<String, Object> data = ImmutableMap.of("vis_state", VisState.DELETED);
        ImmutableMap<String, Object> content = ImmutableMap.of("client", data);
        AccountingResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.response.result.client;
    }

}
