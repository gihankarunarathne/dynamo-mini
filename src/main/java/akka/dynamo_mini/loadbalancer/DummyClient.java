package akka.dynamo_mini.loadbalancer;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

/**
 * Class Description.
 *
 * @author: Gihan Karunarathne
 * Date: 1/15/14
 * Time: 12:35 AM
 * @email: gckarunarathne@gmail.com
 * Auto generate get(), put() calls from dynamo-mini.
 * Testing purpose.
 */
public class DummyClient extends UntypedActor{

    public static Props props(ActorRef clusterClient, Props workExecutorProps, FiniteDuration registerInterval) {
        return Props.create(DummyClient.class, clusterClient, workExecutorProps, registerInterval);
    }

    public static Props props(ActorRef clusterClient, Props workExecutorProps) {
        return props(clusterClient, workExecutorProps, Duration.create(10, "seconds"));
    }

    @Override
    public void onReceive(Object o) throws Exception {

    }
}
