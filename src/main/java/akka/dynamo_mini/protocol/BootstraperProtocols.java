package akka.dynamo_mini.protocol;

import akka.actor.ActorRef;

import java.io.Serializable;

/**
 * These protocols are used to interaction with virtual nodes and bootstraper,
 * introduce a new node to the ring and remove a virtual node etc.
 *
 * Other than that, it contains registering virtual nodes in the Load Balancer.
 *
 * @author: Gihan Karunarathne
 * Date: 1/20/14
 * Time: 9:01 PM
 * @email: gckarunarathne@gmail.com
 */
public interface BootstraperProtocols {

    public static class JoinToRing implements Serializable {
        private final String nodeName;

        public JoinToRing(String key) {
            this.nodeName = key;
        }

        public String getNodeName() {
            return this.nodeName;
        }
    }
    
    public static class LeaveRing implements Serializable{
        private final String nodeName;

        public LeaveRing(String key) {
            this.nodeName = key;
        }

        public String getNodeName() {
            return this.nodeName;
        }
    }

    public static class AddNewNodeToRing implements Serializable {
        private final String nodeName;
        private final ActorRef ref;

        public AddNewNodeToRing(String key,ActorRef ref) {
            this.nodeName = key;
            this.ref = ref;
        }

        public String getNodeName() {
            return this.nodeName;
        }

        public ActorRef getActorRef() {
            return this.ref;
        }
    }

    public static class ACKJoinToRing implements Serializable {
        private final String nodeName;
        private final int numNodes;

        public ACKJoinToRing(String key, int numNodes) {
            this.nodeName = key;
            this.numNodes = numNodes;
        }

        public String getNodeName() {
            return this.nodeName;
        }

        public int getNumNodes() {
            return this.numNodes;
        }
    }

    public static class NewNodeConnected implements Serializable {
    	
    	private final ActorRef nodeRef;
    	
    	public NewNodeConnected(ActorRef refa){
    		this.nodeRef = refa;
    	}
    	
    	public ActorRef getNodeRef(){
    		return nodeRef;
    	}

    	
    }
    
    public static class CurrentRingNode implements Serializable {
        private final String nodeName;
        private final ActorRef ref;

        public CurrentRingNode(String key, ActorRef ref) {
            this.nodeName = key;
            this.ref = ref;
        }

        public String getNodeName() {
            return this.nodeName;
        }

        public ActorRef getActorRef() {
            return this.ref;
        }
    }

    public static class Test implements Serializable{
    	String msg;
    	public Test(String data){
    		this.msg = data;
    	}
    	
    	public String getMsg(){
    		return msg;
    	}
    }
    
    public static class LBUpdateAdd implements Serializable{
        ActorRef ref;
        public LBUpdateAdd(ActorRef ref){
            this.ref = ref;
        }
        
        public ActorRef getRef(){
            return ref;
        }
    }
    
    public static class LBUpdateRemove implements Serializable{
        ActorRef ref;
        public LBUpdateRemove(ActorRef ref){
            this.ref = ref;
        }
        
        public ActorRef getRef(){
            return ref;
        }
    }

}
