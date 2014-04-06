package eu.ipolding.musicportal;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;



public class MusicPortalApplication extends Application<MusicPortalConfiguration>{
	public static void main(String[] args) throws Exception {
		new MusicPortalApplication().run(args);
		
	}

	@Override
	public void initialize(Bootstrap<MusicPortalConfiguration> bootstrap) {
		// nothing to do yet		
	}

	@Override
	public void run(MusicPortalConfiguration arg0, Environment arg1)
			throws Exception {
		// 	nothing to do yet		
	}
	
	
	
	

}
