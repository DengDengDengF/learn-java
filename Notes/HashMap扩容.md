```java
// JDK 1.8 resize() 方法核心逻辑
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;

    // 1. 计算新容量和新阈值
    if (oldCap > 0) {
        if (oldCap >= MAXIMUM_CAPACITY) {  // 已达最大容量，不再扩容
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&  // 容量翻倍
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1;  // 阈值翻倍
    }
    // ... 省略其他分支

    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;

    // 2. 遍历旧数组，迁移元素
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;  // 帮助 GC
                if (e.next == null)
                                        // 单个节点，直接计算新位置
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    // 红黑树拆分 负责逻辑 通过split方法复写了
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else {
                    // 3. 链表拆分：高低位链表
                    Node<K,V> loHead = null, loTail = null;  // 低位链表
                    Node<K,V> hiHead = null, hiTail = null;  // 高位链表
                    Node<K,V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCap) == 0) {
                            // 低位：索引不变
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            // 高位：索引 = 原索引 + 原容量
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);

                    // 4. 将拆分后的链表放到新数组
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;  // 低位放原位置
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;  // 高位放原位置+原容量
                    }
                }
            }
        }
    }
    return newTab;
}
```

---

## 元素重分布原理

```java
hash = ......1  0 1 0 1
扩容前 oldCap = 16 , n = oldCap - 1 = 15 , (n-1) & hash = ......0   0 1 0 1 = 3
扩容后 newCap = oldCap << 1 =  32 , n = newCap - 1 = 31 , (n-1) & hash = ......1   0 1 0 1 = 3 + 2^4 = 19 //原来索引 + 原来容量
----------------------------------------------------------------------------
和新增位有关，可以简单判断，hash & oldCap
例子1：
......1  0 1 0 1
&
......1  0 0 0 0 
_________________
......1  0 0 0 0  

例子2：
......0  0 1 0 1
&
......1  0 0 0 0 
_________________
......0  0 0 0 0    
    
if((hash & oldCap) == 0){
    坐标不变
}else{
    原来索引 + 原来容量
}
    
```

