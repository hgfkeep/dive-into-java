package win.hgfdodo.sort;

import win.hgfdodo.sort.util.Result;

import java.util.Arrays;


public class Sort {

    /**
     * 冒泡排序: 两两之间比较大小，每次可以确定一个元素的位置。
     * 时间复杂度：最坏O(n^2)，最好O(n);
     * 空间复杂度：O(1);
     * 排序稳定性：稳定
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        int tmp;
        for (int i = 0; i < n - 1; i++) {
            boolean changed = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    changed = true;
                }
            }
            //遍历子循环发现没有交换，说明已经全部有序，无需再遍历，直接跳出。
            if (!changed) {
                break;
            }
        }
    }

    /**
     * 选择排序：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     * 时间复杂度：最坏O(n^2)，最好O(n^2);
     * 空间复杂度：O(1);
     * 排序稳定性：不稳定
     */
    public static void selectSort(int[] arr) {
        int n = arr.length;
        int tmp;
        for (int i = 0; i < n; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                tmp = arr[i];
                arr[i] = min;
                arr[minIndex] = tmp;
            }
        }
    }

    /**
     * 插入排序：构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置（左边< 当前值<右边）并插入。
     * 时间复杂度：最坏O(n^2)，最好O(n);
     * 空间复杂度：O(1);
     * 排序稳定性：稳定
     */
    public static void insertSort(int[] arr) {
        int n = arr.length;
        int tmp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                    tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;
                }
            }
        }
    }

    /**
     * 希尔排序: 间距gap分组，每个分组中使用插入排序
     * 时间复杂度：最坏O(n^2)，最好O(n);
     * 空间复杂度：O(1);
     * 排序稳定性：不稳定
     */
    public static void shellSort(int[] arr) {
        int n = arr.length;
        //gap交替进行
        for (int gap = n / 2; gap > 0; gap = gap / 2) {
            //逐个组使用插入排序
            for (int i = gap; i < n; i += gap) {
                int cur = arr[i];
                int j = i - gap;
                for (; j >= 0 && cur < arr[j]; j -= gap) {
                    arr[j + gap] = arr[j];
                }
                arr[j + gap] = cur;
            }
        }
    }

    /**
     * 归并排序：
     * 时间复杂度：最坏O(nlog2 n)，最好O(nlog2 n);
     * 空间复杂度：O(n);
     * 排序稳定性：稳定
     */
    public static int[] mergeSort(int[] arr) {//b表示开始下标，e表示结束下标
        if (arr.length < 2) {
            return arr;
        }
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        return merge(mergeSort(left), mergeSort(right));
    }

    /**
     * 将两个排序排序好的合并在一起;
     * <p>
     * 时间复杂度O(n)
     *
     * @return
     */
    private static int[] merge(int[] left, int[] right) {
        int[] res = new int[left.length + right.length];
        for (int l = 0, r = 0, index = 0; index < res.length; index++) {
            if (l >= left.length) {
                res[index] = right[r++];
            } else if (r >= right.length) {
                res[index] = left[l++];
            } else if (left[l] > right[r]) {
                res[index] = right[r++];
            } else {
                res[index] = left[l++];
            }
        }

        return res;
    }

    /**
     * 快速排序：
     * 时间复杂度：最坏O(n^2)，最好O(nlog2 n);
     * 空间复杂度：O(1);
     * 排序稳定性：不稳定
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        int left = low;
        int right = high;

        //保存基准元素
        int pivot = arr[left];
        while (left < right) {

            //从后向前找比基准元素小的元素
            while (left < right && arr[right] >= pivot)
                right--;
            //从右往左找到第一个符合的元素
            arr[left] = arr[right];
            //从左往右找到比基准元素大的
            while (left < right && arr[left] <= pivot)
                left++;
            arr[right] = arr[left];
        }
        //防止基准值
        arr[left] = pivot;
        quickSort(arr, low, left - 1);
        quickSort(arr, left + 1, high);
    }

    /**
     * 堆排序：
     * 时间复杂度：最坏O(nlog2 n)，最好O(nlog2 n);
     * 空间复杂度：O(1);
     * 排序稳定性：稳定
     */
    public static void heapSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        int l = arr.length;
        //构建堆
        buildHeap(arr, l);

        //循环将堆首与末尾交换，再调整堆
        while (l > 0) {
            int tmp = arr[0];
            arr[0] = arr[l - 1];
            arr[l - 1] = tmp;
            l--;
            adjustHeap(arr, 0, l);
        }

    }

    /**
     * 创建堆
     */
    private static void buildHeap(int[] arr, int l) {
        //从最后一个有子节点的节点开始建立树
        for (int i = (l - 1) / 2; i >= 0; i--) {
            adjustHeap(arr, i, l);
        }
    }

    /**
     * 调整成最大堆
     */
    private static void adjustHeap(int[] arr, int i, int l) {
        int minIndex = i;

        //存在左子树，且左子树 < 根
        if (i * 2 < l && arr[i * 2] > arr[minIndex]) {
            minIndex = i * 2;
        }
        //如果有右子树，且右子树 < 根
        if (i * 2 + 1 < l && arr[i * 2 + 1] > arr[minIndex]) {
            minIndex = i * 2 + 1;
        }

        //根不是最小，需要调整
        if (i != minIndex) {
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;

            //调整了，可能还需要继续深入调整，递归深入调整
            adjustHeap(arr, minIndex, l);
        }
    }


    /**
     * 基数排序
     * 时间复杂度：最坏O(d*(n+r))，最好O(d*(n+r)); 其中d位数，r基数，n元素个数
     * 空间复杂度：O(n+r);
     * 排序稳定性：稳定
     */
    public static void radixSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        int max = 0;
        //计算最大数的位数
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }

        int digit = 0;
        while (max > 0) {
            digit++;
            max = max / 10;
        }

        int[][] buckets = new int[10][arr.length];
        int base = 10;

        //从低位到高位，对每一位遍历，将所有元素分配到桶中
        for (int i = 0; i < digit; i++) {
            //存储各个桶中存储元素的数量
            int[] bkLen = new int[10];
            //将所有元素分配到桶中
            for (int j = 0; j < arr.length; j++) {
                //计算每个元素base/10位上的数字，进入桶
                int index = (arr[j] % 10) / (base / 10);
                buckets[index][bkLen[index]] = arr[j];
                bkLen[index]++;
            }

            //收集：将不同桶里数据挨个捞出来,为下一轮高位排序做准备,由于靠近桶底的元素排名靠前,因此从桶底先捞
            int k = 0;
            for (int b = 0; b < buckets.length; b++) {
                for (int p = 0; p < bkLen[b]; p++) {
                    arr[k++] = buckets[b][p];
                }
            }
            System.out.println("sorting: " + Arrays.toString(arr));
            base *= 10;
        }

    }


    public static void main(String[] args) {
        int[] a = new int[]{4, 6, 1, 5, 23, 5, 2};
//        bubbleSort(a);
//        selectSort(a);
//        insertSort(a);
//        shellSort(a);
//        a = mergeSort(a);
//        quickSort(a, 0, a.length - 1);
//        heapSort(a);
        radixSort(a);
        Result.print(a);
    }
}
