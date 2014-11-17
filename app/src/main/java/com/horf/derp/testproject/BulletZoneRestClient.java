package com.horf.derp.testproject;

/**
 * Created by sji363 on 11/12/14.
 * So far the questions I am getting
 * are related to LongWrapper and BooleanWrapper.
 * Please make sure the name of the variable in both
 * classes is named "result" with type of long and boolean
 * respectively. Moreover, you need to provide a default constructor
 * for both classes.
 *
 */
import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;

@Rest(rootUrl = "http://stman1.cs.unh.edu:6191/games",
        converters = {MappingJackson2HttpMessageConverter.class}
)
public interface BulletZoneRestClient extends RestClientErrorHandling {
    void setRootUrl(String rootUrl);

    @Post("")
    LongWrapper join() throws RestClientException;

    @Get("")
    GridWrapper grid();

    @Put("/{tankId}/move/{direction}")
    BooleanWrapper move(long tankId, byte direction);

    @Put("/{tankId}/turn/{direction}")
    BooleanWrapper turn(long tankId, byte direction);

    @Put("/{tankId}/fire")
    BooleanWrapper fire(long tankId);

    @Delete("/{tankId}/leave")
    BooleanWrapper leave(long tankId);
}