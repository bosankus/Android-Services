//package com.androidplay.services.view.main
//
//import com.androidplay.services.BaseContract
//import com.androidplay.services.MainCoroutineRule
//import com.androidplay.services.dispatcher.DispatcherProvider
//import com.androidplay.services.model.model.Weather
//import com.androidplay.services.model.repository.WeatherRepository
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.MockitoAnnotations
//import org.mockito.junit.MockitoJUnitRunner
//
//@RunWith(MockitoJUnitRunner::class)
//@OptIn(ExperimentalCoroutinesApi::class)
//class MainInteractorTest {
//
//    @Mock
//    private lateinit var listener: BaseContract.Interactor.OnFinishedListener
//
//    @Mock
//    private lateinit var dispatcher: DispatcherProvider
//
//    @Mock
//    private lateinit var repository: WeatherRepository
//
//    @Mock
//    private lateinit var interactor: BaseContract.Interactor
//
//    private lateinit var mainInteractor: MainInteractor
//    private lateinit var mockedWeather: Weather
//
//    @get: Rule
//    val mainCoroutineRule = MainCoroutineRule()
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        mainInteractor = MainInteractor(repository, dispatcher, mainCoroutineRule.testScope)
//        mockedWeather = Weather(
//            id = 1275004,
//            main = Weather.Main(temp = 298.12),
//            name = "Kolkata"
//        )
//    }
//
//
//    @Test
//    fun requestData_onEmptyLocationParameter_returnsFailed() =
//        mainCoroutineRule.testScope.runTest {
//            doNothing(). `when`(interactor.requestData("", listener))
//            `when`(repository.getWeather("")).thenReturn(mockedWeather)
//            mainInteractor.requestData("", listener)
//
//            verify(repository, times(1)).getWeather("")
//            //`when`(interactor.requestData("", listener)).thenReturn(listener.onFailed(""))
//        }
//}
//
