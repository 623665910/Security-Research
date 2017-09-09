package spider;

import java.util.*;     //�����ǣ�����html��ҳ������ҳ����ȡURL

import org.htmlparser.Node;//���νڵ����
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
public class HtmlParserTool 
{
	/**
	 * ��ȡһ����վ�ϵ����� ��filter�����������ӻ�ȡ�����ӣ�
	 * urlΪ��ҳurl��filter�����ӹ����������ظ�ҳ�������ӵ�HashSet
	 * @return
	 */
	public static Set<String>extracLinks(String url , LinkFilter filter)
	{
		Set<String>links = new HashSet<String>();
		try
		{
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			//����<frame>��ǩ��filter��������ȡframe��ǩ���src����
			NodeFilter frameFilter = new NodeFilter()
			{
				public boolean accept(Node node)
				{
					if(node.getText().startsWith("frame src="))
						return true;
					else
						return false;
				}
			};
			//OrFilter�����ù���<a>��ǩ��<frame>��ǩ
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
			
			//�õ����о������˵ı�ǩ
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for(int i=0;i<list.size();i++)
			{
				Node tag=list.elementAt(i);
				if(tag instanceof LinkTag)     //<a>��ǩ
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();//�������
					if(filter.accept(linkUrl))
						links.add(linkUrl);   //����HashSet
				}
				else//<frame>��ǩ
				{
					//��ȡframe��src���Ե����� �� ��<framesrc="test.html"/ >
					String frame = tag.getText();
					int start = frame.indexOf("src=");
					/**
					 * URL	�涨Ҫ�ڿ������ʾ���ĵ��ĵ�ַ��
					 * ���ܵ�ֵ��
					 *		���� URL - ָ������վ�㣨���� src="www.example.com/index.html"��
					 *		��� URL - ָ��վ���ڵ��ļ������� src="index.html"��
					 */
					
					frame = frame.substring(start);
					int end = frame.indexOf(" ");
					if(end==-1)
						end=frame.indexOf(">");
					String frameUrl = frame.substring( 5 , end-1 );
					if(filter.accept(frameUrl))
						links.add(frameUrl);
				}//if
			}//for
		}//try
		catch(ParserException e)
		{
			e.printStackTrace();
		}
		return links;
	}
}