package classes;

import java.util.LinkedList;

public class Queue                  //   ���潫Ҫ���ʵ�URL
{
		// ʹ������ʵ�ֶ���
		private LinkedList queue=new LinkedList();
		
		// �����
		public void enQueue(Object url)
		{
			queue.addLast(url);
		}
		
		// ������
		public Object deQueue()
		{
			return queue.removeFirst();
		}
		// �ж϶����Ƿ�Ϊ��
		public boolean isQueueEmpty()
		{
			return queue.isEmpty();
		}
		// �ж϶����Ƿ����t
		public boolean contians(Object t)
		{
			return queue.contains(t);
		}
		public boolean empty()
		{
			return queue.isEmpty();
		}
}
