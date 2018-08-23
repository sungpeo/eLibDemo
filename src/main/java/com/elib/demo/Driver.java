package com.elib.demo;

import com.elib.demo.infrastructure.persistence.Persistence;
import com.elib.demo.infrastructure.persistence.QueryPersistence;

public class Driver {
	public static void main(String[] args) {

		Persistence persistence = QueryPersistence.getInstance();
		Dispatcher dispatcher = Dispatcher.createDispatcher(persistence);
		new CmdUI(dispatcher).start();
	}

}
