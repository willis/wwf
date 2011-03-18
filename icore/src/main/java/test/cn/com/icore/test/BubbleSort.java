package cn.com.icore.test;

public class BubbleSort {

	/**
	 * @param args
	 */
	public static void main(String... args) {
		// TODO Auto-generated method stub
		int[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		sort(data);
		for (int i = 0; i < data.length; i++)
			System.out.print(data[i] + " ");
		//System.out.println(2<<3);
		
	}

	public static void sort(int... data) {
		int temp;
		int m = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = data.length - 1; j > i; j--) {
				m ++;
				System.out.format("Row :[%d %d]\n", m, j);
				if (data[i] > data[j]) {
					temp = data[i];
					data[i] = data[j];
					data[j] = temp;
				}
			}
		}
	}
	
	

}
