JVector is simple tool.
======================

Tool as a result of having calculated by 2D finite element method, to easily read (vector figure). 

実行方法
-------

    $ java -jar jvector.jar

ファイル指定方法
---------------

1. メニュー(File->Open)から
2. 描画領域上にデータファイルをDrag&Drop

ファイルフォーマット
------------------

<pre>
 1行目
   [節点数(NA)] [要素数(NE)] [要素内の節点数(LE)] 0
 2行目〜          ------ 節点の座標
   [X座標] [y座標]
 NA+2行目〜       ------ 要素を構成する節点のIndex値
   [1番目] [2番目] [3番目] [4番目]
 NA+2+NE行目〜    ------ 節点上の値
   [ベクトル値(X)] [ベクトル値(Y)] [スカラー値]
</pre>

未対応
------

* 要素値の読み込み
* ベクターイメージでの保存
* ファイル名、解像度指定のPNG形式保存（固定では可）
