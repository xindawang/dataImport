import java.io.File;
/*
 * 获取一个目录下的所有文件夹和文件，包括子文件夹和子文件 。
 * 并将文件夹和文件名称打印在控制台上面。并且要显示文件目录的层级
 * 注：运用了递归的算法。
 */
public class getSubFile {
    public static int fileNumber = 0;

    public static void main(String[] args) {
        File dir=new File("D:\\航空工业\\交付物\\0413\\供货管理WBS与表单清理\\");
        //File dir=new File("F:\\");
        //如果使用上述的盘符的根目录，会出现java.lang.NullPointerException
        //为什么？
        getAllFiles(dir,0);//0表示最顶层
        System.out.println(fileNumber);
    }
    //获取层级的方法
    public static String getLevel(int level)
    {
        //A mutable sequence of characters.
        StringBuilder sb=new StringBuilder();
        for(int l=0;l<level;l++)
        {
            sb.append("|--");
        }
        return sb.toString();
    }
    public static void getAllFiles(File dir,int level)
    {
        System.out.println(getLevel(level)+dir.getName());
        level++;
        File[] files=dir.listFiles();
        for(int i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
            {
                //这里面用了递归的算法
                getAllFiles(files[i],level);
            }
            else {
                System.out.println(getLevel(level)+files[i].getName());
                fileNumber++;
            }
        }
    }
}
