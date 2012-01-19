/* Chinese segmentation */
package cn.vivame.v2.gene.util;

/**
 * @author niko
 */
import java.io.*;
import java.util.*;

public class SegmentationChs {
        public static final char DELIMITER = '@';
        static final String DATAPATH = "";

        HashSet dict = new HashSet ();
        int maxWordSize = 0;
        String w;

        public SegmentationChs (String dictFileName) {
                try {
                        BufferedReader dictInput = new BufferedReader (
                                                                                        new FileReader (DATAPATH + dictFileName));

                        String buffer;
                        while ((buffer = dictInput.readLine()) != null) {
                                dict.add(buffer);
                                if (buffer.length() > maxWordSize) {
                                        maxWordSize = buffer.length();
                                        w = buffer;
                                }
                        }
            }
            catch (Exception ex) {
                    ex.printStackTrace(System.err);
            }
        }

        public boolean search (String str) {
                return dict.contains(str);
        }

        public String segMore (String rawString) {
                int total	= rawString.length();
                int start	= 0;
                int length	= 0;
                String result = new String();

                while (start < total) {
                        for (	length = 1;
                                        length <= maxWordSize && start + length <= total;
                                        length++)
                        {
                                if (search(rawString.substring(start, start + length))) {
                                        // find the shortest string
                                        if (start + length + 1 <= total) {	// search one char more
                                                if (search(rawString.substring(start, start + length + 1))) {
                                                        length++;
                                                }
                                        }
                                        break;
                                }
                        }

                        if (length <= maxWordSize && start + length <= total) {
                                // search succeeded
                                result	+= rawString.substring(start, start + length);
                                result	+= DELIMITER;
                                start	+= length;
                        } else {// search failed
                                start++;
                        }
                }

                return result.substring(0, result.length() - 1);
        }

        public String segAll (String rawString) {
                int total	= rawString.length();
                int start	= 0;
                int length	= 0;
                String result = new String();

                while (start < total) {
                        for (	length = 1;
                                        length <= maxWordSize && start + length <= total;
                                        length++)
                        {
                                if (search(rawString.substring(start, start + length))) {
                                        // find the shortest string
                                        result += rawString.substring(start, start + length);
                                        result += DELIMITER;
                                }
                        }

                        start++;
                }

                return result.substring(0, result.length() - 1);
        }

        public String segShort (String rawString) {
                int total	= rawString.length();
                int start	= 0;
                int length	= 0;
                String result = new String();

                while (start < total) {
                        for (	length = 1;
                                        length <= maxWordSize && start + length <= total;
                                        length++)
                        {
                                if (search(rawString.substring(start, start + length))) {
                                        // find the shortest string
                                        break;
                                }
                        }

                        if (length <= maxWordSize && start + length <= total) {
                                // search succeeded
                                result	+= rawString.substring(start, start + length);
                                result	+= DELIMITER;
                                start	+= length;
                        } else {// search failed
                                start++;
                        }
                }

                return result.substring(0, result.length() - 1);
        }

        public String segLong (String rawString) {
                int total	= rawString.length();
                int start	= 0;
                int end		= 0;
                int length	= 0;
                String result = new String();

                while (start < total) {
                        end = start;

                        for (	length = 1;
                                        length <= maxWordSize && start + length <= total;
                                        length++)
                        {
                                if (search(rawString.substring(start, start + length))) {
                                        // find the shortest string
                                        end = start + length;
                                }
                        }

                        if (end > start) {// search succeeded
                                result	+= rawString.substring(start, end);
                                result	+= DELIMITER;
                                start	= end;
                        } else {// search failed
                                start++;
                        }
                }

                return result.substring(0, result.length() - 1);
        }

        public static void main (String[] args)
    {
        	String path = SegmentationChs.class.getResource("").getPath()+"dict/dict.txt";
        	SegmentationChs seg = new SegmentationChs (path);

            System.out.println (seg.segShort("是一个汉语常用词库，收录词汇量超过10万，另外还自带了一个拼音加加输入法，黑马公司的难读字查音工具。利用一些输入法的“词组导入”功能，你可以给自己的输入法扩充词库，让它更好地为你服务。该词库可成功添加到紫光拼音,拼音加加,智能陈桥,五笔字型等多种输入法内,对于习惯词组输入法的电脑用户有很大的帮助.他可以在不改变用户的输入法习惯的同时,大幅度提高打字的速度和效率,对于拼音输入法,它可以大大降低用户按翻页键的次数,让拼音打字有如行云流水。"));
            System.out.println (seg.segLong("计算机系统结构"));
            System.out.println (seg.segAll("计算机系统结构"));
            System.out.println (seg.segMore("计算机系统结构")); // this works well!

    }
}
