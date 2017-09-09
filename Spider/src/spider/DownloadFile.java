package spider;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
public class DownloadFile        //��ҳ���ز�����
<getResponseBodyAsStream>
{
	/**
	 * ����URL����ҳ����������Ҫ�������ҳ���ļ�����ȥ��URL�еķ��ļ����ַ�
	 */
	public String getFileNameByUrl(String url, String contentType)
	{
		//�Ƴ�http
		url = url.substring(7);
		//text/html����
		if(contentType.indexOf("html")!=-1)
		{
			url = url.replace("[\\?/:*|<>\"]", "_")+".html";
			return url;
		}
		//application/pdf����
		else 
		{
			return url.replaceAll("[\\?/:*<>\"]", "_")+"."+contentType.substring(contentType.lastIndexOf("/")+1);
		}
	}
	/**
	 * ������ҳ�ֽ����鵽�����ļ���filePathΪҪ������ļ�����Ե�ַ
	 */
	
	private  String get_right_filename(String filename)
	{
		String res="";
		for(char c:filename.toCharArray())
			if(c!='/'&&c!='\\'&&c!=':'&&c!='*'&&c!='?'&&c!='"'&&c!='>'&&c!='<'&&c!='|') res+=c;
		return res;
	}
	private void saveToLocal(byte[] data,String filePath)
	{
		System.out.println("�ļ�ԭ·��Ϊ"+filePath);
		
		String path0=filePath.substring(0,filePath.lastIndexOf("/")+1);
		String path1=filePath.substring(filePath.lastIndexOf("/")+1,filePath.length());
		path1=get_right_filename(path1);
		
		filePath=path0+path1;
		System.out.println("�ļ������·��Ϊ"+filePath);
		File f = new File(path0);
        // �����ļ���
        if (!f.exists()) {
            f.mkdirs();
        }
        
        
		try
		{
			DataOutputStream out;
			out = new DataOutputStream(new FileOutputStream(new File(filePath)));
			for(int i=0;i< data.length;i++)
				out.write(data[i]);
			out.flush();
			out.close();
		}
		catch (IOException e) 
		{
			
			System.out.println("�����ļ�����");
			e.printStackTrace();
		}
	}
	//����URLָ�����ҳ
	public String downloadFile(String url)
	{
		String filePath = null;
		
		//1.����HttpClient�������ò���
		HttpClient httpClient = new HttpClient();
		
		//����HTTP���ӳ�ʱ5s
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		
		//2.����GetMethod�������ò���
		GetMethod getMethod = new GetMethod(url);
		
		//����get����ʱ5s
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		
		//�����������Դ���
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		//3.ִ��HTTP GET����
		try 
		{
			int statusCode;
			statusCode = httpClient.executeMethod(getMethod);
			//�ж�״̬��
			if(statusCode != HttpStatus.SC_OK);
			{
				System.err.println("Method failed: "+getMethod.getStatusLine());
				filePath = null;
			}
			//4.����HTTP��Ӧ����
			byte[] responseBody = getMethod.getResponseBody();//��ȡΪ�ֽ�����
			
			//System.out.println(responseBody.length);
			//������ҳurl���ɱ���ʱ���ļ���
			filePath = "temp/"+getFileNameByUrl(url,getMethod.getResponseHeader("Content-Type").getValue());
			
			//filePath="E:\\"+url.substring(7, url.length())+".html";
			
			saveToLocal(responseBody,filePath);
			
			
		}
		catch(HttpException e)
		{
			//�����������쳣��������Э�鲻�Ի��߷��ص�����������
			System.out.println("������ַ�Ƿ���ȷ!");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			//���������쳣
			e.printStackTrace();
		}
		finally
		{
			//�ͷ�����
			getMethod.releaseConnection();
		}
		return filePath;		
	}
}
