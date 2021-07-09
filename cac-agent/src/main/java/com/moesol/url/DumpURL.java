package com.moesol.url;

import java.io.BufferedInputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;

import com.moesol.cac.agent.CacHookingAgent;

public class DumpURL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CacHookingAgent.premain(null, null);
		        if (CookieHandler.getDefault() == null)
		            CookieHandler.setDefault(
		                new CookieManager(null, CookiePolicy.ACCEPT_ALL));

		    for (int i = 0; i < 4; i++) {
			long start = System.currentTimeMillis();
			URL url = new URL(args[0]);
			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			try {
				if (i == 0) {
					System.out.println("---ready---");
					start = System.currentTimeMillis();
				}
				byte[] buf = new byte[4096];
				while (true) {
					int r = bis.read(buf);
					if (r <= 0) {
						break;
					}
					System.out.write(buf, 0, r);
				}
			} finally {
				bis.close();
			}
			long end = System.currentTimeMillis();
			long delta = end - start;
			System.out.println("Downloaded, took " + delta + " ms");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
