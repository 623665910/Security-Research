package classes;

import java.util.HashSet;
import java.util.Set;
import classes.Queue;

public class LinkQueue           // ��¼�Ѿ������ʵ�URL��
{
	/**ѡ��HashSet��ԭ��
	 * �ṹ�б����URL�����ظ���
	 * �ܹ����ٵز��� (ʵ��ϵͳ��URL����Ŀ�ǳ��࣬���Ҫ���ǲ�������) ��
	 */
	
	//�ѷ��ʵ�url����
	private static Set visitedUrl = new HashSet();
	
	//�����ʵ�url����
	private static Queue unVisitedUrl = new Queue();
	
	//���δ���ʵ�URL
	public static Queue getUnVisitedUrl()
	{
		return unVisitedUrl;
	}
	// ��ӵ����ʹ���URL������
	public static void addVisitedUrl(String url)
	{
		visitedUrl.add(url);
	}
	// �Ƴ����ʹ���URL
	public static void removeVisitedUrl(String url)
	{
		visitedUrl.remove(url);
	}
	// δ���ʵ�URL������
	public static Object unVisitedUrlDeQueue()
	{
		return unVisitedUrl.deQueue();
	}
	//��֤ÿ��URLֻ������һ��
	public static void addUnvisitedUrl(String url)
	{
		if(url!=null&&!url.trim().equals("")&&!visitedUrl.contains(url)&&!unVisitedUrl.contians(url))
			unVisitedUrl.enQueue(url);
	}
	// ����Ѿ����ʵ�URL��Ŀ
	public static int getVisitedUrlNum()
	{
		return visitedUrl.size();
	}
	// �ж�δ���ʵ�URL�������Ƿ�Ϊ��
	public static boolean unVisitedUrlsEmpty()
	{
		return unVisitedUrl.empty();
	}
}
