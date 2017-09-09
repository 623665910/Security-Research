package spider;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import classes.LinkQueue;

public class MyCrawler				//�����������������
{
	/**
	 * ʹ�����ӳ�ʼ��URL����
	 */
	private void initCrawlerWithSeeds(String[] seeds)
	{
		for(int i=0;i<seeds.length;i++)
		{
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}
	/**
	 * ץȡ����
	 */
	public void crawling(String[]seeds)
	{
		//�������������ȡ��http://www.baidu.com ��ͷ������
		LinkFilter filter = new LinkFilter()
		{
			public boolean accept(String url)
			{
				if(url.startsWith("http://blog.sina.com.cn"))
					return true;
				else 
					return false;
			}
		};
		//��ʼ��URL����
		initCrawlerWithSeeds(seeds);
		
		//ѭ����������ץȡ�����Ӳ�����ץȡ����ҳ������100
		while(!LinkQueue.unVisitedUrlsEmpty()&&LinkQueue.getVisitedUrlNum()<=1000)//�����������
		{
			//��ͷURL������
			String visitUrl = (String)LinkQueue.unVisitedUrlDeQueue();
			if(visitUrl==null)
				continue;
			
			DownloadFile downLoader = new DownloadFile();
			//������ҳ
			downLoader.downloadFile(visitUrl);
			
			//��URL�����ѷ��ʵ�URL��
			LinkQueue.addVisitedUrl(visitUrl);
			
			//��ȡ��������ҳ�е�URL
			Set<String>links = HtmlParserTool.extracLinks(visitUrl, filter);
			
			//�µ�δ���ʵ�URL���
			for(String link:links)
			{
				LinkQueue.addUnvisitedUrl(link);
			}
		}
		
	}
	
	public static void main(String [] args) throws IOException
	{
		MyCrawler crawler = new MyCrawler();
		String url="http://www.baidu.com";
		String url1="http://blog.sina.com.cn";
		crawler.crawling( new String[] {url1} );
		System.out.println("main���������");
	}
}