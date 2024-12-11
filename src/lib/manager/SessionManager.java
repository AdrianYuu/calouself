package lib.manager;

import model.User;

public final class SessionManager {
	private static User currentUser;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		SessionManager.currentUser = currentUser;
	}
	
	
}
