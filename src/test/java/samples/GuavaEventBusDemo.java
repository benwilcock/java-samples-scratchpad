package samples;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by ben on 31/03/16.
 */
public class GuavaEventBusDemo {

    //Note: EventBus can't use Mock's as subscribers, because they lack @Subscribe annotated methods!
    private static EventBus eventBus;

    interface EventA {}
    interface EventB {}

    class Subscriber{

        private int count = 0;

        @Subscribe
        public void onEvent(EventA event){
            count++;
        }

        @Subscribe
        public void onEvent(EventB event){
            count++;
        }

        public int getEventCount() {
            return count;
        }
    }

    @BeforeClass
    public static void setup(){
        eventBus = new EventBus();
    }

    @Test
    public void shouldRouteOneEventToOneSubscriberWithoutMockito() {
        //Arrange
        Subscriber subscriber = new Subscriber();
        eventBus.register(subscriber);

        //Assure
        assertEquals(0, subscriber.getEventCount());

        //Act
        eventBus.post(new EventA() {});

        //Assert
        assertEquals(1, subscriber.getEventCount());
    }

    @Test
    public void shouldRouteMultipleEventsToOneSubscriber() {
        //Arrange
        Subscriber subscriber = spy(new Subscriber());
        EventA eventA = mock(EventA.class);
        EventB eventB = mock(EventB.class);
        eventBus.register(subscriber);

        //Act
        eventBus.post(eventA);
        eventBus.post(eventB);

        //Assert
        verify(subscriber, times(1)).onEvent(eventA);
        verify(subscriber, times(1)).onEvent(eventB);
        assertEquals(2, subscriber.getEventCount());
    }

    @Test
    public void shouldRouteOneEventToMultipleSubscribers() {
        //Arrange
        Subscriber subscriberA = spy(new Subscriber());
        Subscriber subscriberB = spy(new Subscriber());
        EventA event = mock(EventA.class);
        eventBus.register(subscriberA);
        eventBus.register(subscriberB);

        //Act
        eventBus.post(event);

        //Assert
        verify(subscriberA, times(1)).onEvent(event);
        verify(subscriberB, times(1)).onEvent(event);

        assertEquals(1, subscriberA.getEventCount());
        assertEquals(1, subscriberB.getEventCount());
    }
}
