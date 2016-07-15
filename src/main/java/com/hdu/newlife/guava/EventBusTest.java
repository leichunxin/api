package com.hdu.newlife.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * EventBus是Guava的事件处理机制，是设计模式中的观察者模式（生产/消费者编程模型）的优雅实现。对于事件监听和发布订阅模式，
 *  EventBus是一个非常优雅和简单解决方案，我们不用创建复杂的类和接口层次结构。 Observer模式是比较常用的设计模式之一，
 *  虽然有时候在具体代码里，它不一定叫这个名字， 比如改头换面叫个Listener，但模式就是这个模式。
 *  手工实现一个Observer也不是多复杂的一件事， 只是因为这个设计模式实在太常用了，
 *  Java就把它放到了JDK里面：Observable和Observer，从JDK 1.0里，
 *   它们就一直在那里。从某种程度上说，它简化了Observer模式的开发，至少我们不用再手工维护自己的Observer列表了。 
 *   不过，如前所述，JDK里的Observer从1.0就在那里了，直到Java 7，它都没有什么改变，就连通知的参数还是Object类型。
 *    要知道，Java 5就已经泛型了。Java 5是一次大规模的语法调整，许多程序库从那开始重新设计了API，
 *    使其更简洁易用。 当然，那些不做应对的程序库，多半也就过时了。 这也就是这里要讨论知识更新的原因所在。
 *    今天，对于普通的应用，如果要使用Observer模式该如何做呢？答案是Guava的EventBus。
 * 
 * 
 * EventBus基本用法：
 * 
 * 使用Guava之后, 如果要订阅消息, 就不用再继承指定的接口, 只需要在指定的方法上加上@Subscribe注解即可。
 * 
 * MultiListener的使用：
 * 
 * 只需要在要订阅消息的方法上加上@Subscribe注解即可实现对多个消息的订阅
 * 
 * @author lenovo
 *
 */
public class EventBusTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventBus eventBus = new EventBus("test");
		EventListener listener = new EventListener();
		eventBus.register(listener);
		eventBus.post(new TestEvent(200));
		eventBus.post(new TestEvent(300));
		eventBus.post(new TestEvent(400));
		System.out.println("LastMessage:" + listener.getLastMessage());

		MultipleListener multiListener = new MultipleListener();
		eventBus.register(multiListener);
		eventBus.post(new Integer(100));
		eventBus.post(new Integer(200));
		eventBus.post(new Integer(300));
		eventBus.post(new Long(800));
		eventBus.post(new Long(800990));
		eventBus.post(new Long(800882934));
		System.out.println("LastInteger:" + multiListener.getLastInteger());
		System.out.println("LastLong:" + multiListener.getLastLong());
	}

}

class TestEvent {
	private final int message;
	public TestEvent(int message) {
		this.message = message;
		System.out.println("event message:" + message);
	}

	public int getMessage() {
		return message;
	}
}

class EventListener {
	public int lastMessage = 0;
	@Subscribe
	public void listen(TestEvent event) {
		lastMessage = event.getMessage();
		System.out.println("Message:" + lastMessage);
	}

	public int getLastMessage() {
		return lastMessage;
	}
}

class MultipleListener {
	public Integer lastInteger;
	public Long lastLong;

	@Subscribe
	public void listenInteger(Integer event) {
		lastInteger = event;
		System.out.println("event Integer:" + lastInteger);
	}

	@Subscribe
	public void listenLong(Long event) {
		lastLong = event;
		System.out.println("event Long:" + lastLong);
	}

	public Integer getLastInteger() {
		return lastInteger;
	}

	public Long getLastLong() {
		return lastLong;
	}
}
