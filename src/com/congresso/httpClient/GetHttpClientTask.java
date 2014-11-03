package com.macaxeira.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class GetHttpClientTask extends HttpClientTaskAbstract {
	
	protected String executaHttp(String url) throws Exception {
		BufferedReader bufferreader = null;

		try{
			HttpClient client = getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.setURI(new URI(url));
			HttpResponse httpResponse = client.execute(httpGet);
			
			
			bufferreader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while((line = bufferreader.readLine()) != null){
				stringBuffer.append(line + LS);
			}
			bufferreader.close();

			String resultado = stringBuffer.toString();			
			return resultado;

		}finally{
			if(bufferreader != null){
				try{
					bufferreader.close();
				}catch(IOException e){
					e.printStackTrace();
				}               
			}           
		}
	}

}
