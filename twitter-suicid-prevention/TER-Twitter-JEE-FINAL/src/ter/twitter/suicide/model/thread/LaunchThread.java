package ter.twitter.suicide.model.thread;

public class LaunchThread {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void launch() throws InterruptedException {
		// -> Threads 

		ThreadSelectThematics thread  = new ThreadSelectThematics("mental state","hurt");
		ThreadSelectThematics thread1 = new ThreadSelectThematics("mental state","loneliness");
		ThreadSelectThematics thread2 = new ThreadSelectThematics("mental state","fear");
		ThreadSelectThematics thread3 = new ThreadSelectThematics("mental state","depression");
		ThreadSelectThematics thread4 = new ThreadSelectThematics("Method","Method");
		ThreadSelectThematics thread5 = new ThreadSelectThematics("insults","insults");
		ThreadSelectThematics thread6 = new ThreadSelectThematics("anorexia","anorexia");
		ThreadSelectThematics thread7 = new ThreadSelectThematics("mental state","lonely");
		ThreadSelectThematics thread8 = new ThreadSelectThematics("mental state","sentence");
		ThreadSelectThematics thread9 = new ThreadSelectThematics("cyberbullying","cyberbullying");
		ThreadSelectAllTweets threadtwitter = new ThreadSelectAllTweets();

		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
		thread7.start();
		thread8.start();
		thread9.start();
		threadtwitter.start();

		thread.join();
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		thread5.join();
		thread6.join();
		thread7.join();
		thread8.join();
		thread9.join();
		threadtwitter.join();

	}
}
