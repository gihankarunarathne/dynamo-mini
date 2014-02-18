package akka.dynamo_mini.loadbalancer;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.MemberUp;
import akka.dynamo_mini.protocol.BootstraperProtocols.LBUpdateAdd;
import akka.dynamo_mini.protocol.BootstraperProtocols.LBUpdateRemove;
import akka.dynamo_mini.protocol.BootstraperProtocols.Test;
import akka.dynamo_mini.protocol.ClientProtocols.ReadRequest;
import akka.dynamo_mini.protocol.ClientProtocols.WriteRequest;
import akka.dynamo_mini.protocol.VirtualNodeProtocols.GetKeyValue;
import akka.dynamo_mini.protocol.VirtualNodeProtocols.PutKeyValue;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinRouter;
import akka.routing.SmallestMailboxRouter;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

/**
 * Class Description.
 * 
 * @author: Gihan Karunarathne Date: 1/15/14 Time: 12:35 AM
 * @email: gckarunarathne@gmail.com Auto generate get(), put() calls from dynamo-mini. Testing
 *         purpose.
 */
public class LoadBalancer extends UntypedActor {

    private List<ActorRef> ringMembers = new ArrayList<ActorRef>();
    Cluster cluster = Cluster.get(getContext().system());

    ActorRef router;

    @Override
    public void preStart() {
        System.out.println("************* Load Balancer Started ************");
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof LBUpdateAdd) {
            LBUpdateAdd newNodeMsg = (LBUpdateAdd) message;
            ringMembers.add(newNodeMsg.getRef());
            System.out.println(" *** # of nodes in Load Balancer List : " + ringMembers.size());
            router = getContext().actorOf(
                    Props.empty().withRouter(SmallestMailboxRouter.create(ringMembers)));
            router.tell(new Test("** Message from Load Balancer to a node via ROUTER...."),
                    getSelf());
        } 
        else if (message instanceof LBUpdateRemove) {
            LBUpdateRemove nodeRemoveMsg = (LBUpdateRemove) message;
            ringMembers.remove(nodeRemoveMsg.getRef());
            System.out.println(" *** # of nodes in Load Balancer List : " + ringMembers.size());
            router = getContext().actorOf(
                    Props.empty().withRouter(SmallestMailboxRouter.create(ringMembers)));
        }else if (message instanceof WriteRequest) {
            router.tell(message, getSender());
        } else if (message instanceof ReadRequest) {

        } else {
            unhandled(message);
        }
    }
}
