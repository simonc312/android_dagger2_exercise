package com.simonc312.androidapiexercise;

import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.api.models.Guide;
import com.simonc312.androidapiexercise.api.models.Guides;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Simon on 5/29/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GuideInteractorTest {
    private GuideInteractor guideInteractor;
    @Mock
    private ApiService mockApiService;
    @Mock
    Call<Guides> mockCall;
    @Mock
    private GuideRepository mockGuideRepository;
    @Mock
    private GuideInteractor.InteractorOutput mockOutput;

    @Before
    public void setUp() throws Exception {
        this.guideInteractor = new GuideInteractor(
                mockApiService,
                mockGuideRepository
                );
        this.guideInteractor.setOutput(mockOutput);
    }

    @After
    public void tearDown() throws Exception {
        this.guideInteractor.setOutput(null);
        this.guideInteractor = null;
    }

    @Test
    public void getWithWifi() throws Exception {
        doReturn(mockCall).when(mockApiService).getUpcomingGuides();
        this.guideInteractor.get();
        verify(mockApiService, times(1)).getUpcomingGuides();
        verify(mockCall, times(1)).enqueue(this.guideInteractor);
    }

    @Test
    public void getWithQuery() throws Exception {
        final String fakeQuery = "World Wide Wrestling Federation Conference";
        this.guideInteractor.get(fakeQuery);
        verify(mockGuideRepository, times(1)).get(fakeQuery, this.guideInteractor);
    }

    @Test
    public void cancel() throws Exception {
        doReturn(mockCall).when(mockApiService).getUpcomingGuides();
        this.guideInteractor.get();
        this.guideInteractor.cancel();
        verify(mockCall, times(1)).cancel();
    }

    @Test
    public void handleRetrofitResponse() throws Exception {
        final List<Guide> fakeGuides = new ArrayList<>(0);
        this.guideInteractor.handleRetrofitResponse(fakeGuides);
        verify(mockOutput, times(1)).onGuidesAvailable(fakeGuides);
        verify(mockGuideRepository, times(1)).add(fakeGuides);
    }

    @Test
    public void handleRetrofitFailure() throws Exception {
        final Throwable fakeThrowable = mock(Throwable.class);
        this.guideInteractor.handleRetrofitFailure(fakeThrowable);
        verify(mockOutput, times(1)).onGuidesUnavailable();
    }

    @Test
    public void handleRetrofitFailureNoWifi() throws Exception {
        final Throwable fakeThrowable = mock(UnknownHostException.class);
        this.guideInteractor.handleRetrofitFailure(fakeThrowable);
        verify(mockGuideRepository, times(1)).get("", guideInteractor);
        verify(mockOutput, times(1)).onGuidesUnavailable();
    }

}