/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.xinyi_tech.commonlibs.util;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.support.annotation.Keep;
import android.view.Gravity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DrawableUtils {

    private DrawableUtils() {
    }

    /**
     * 根据Uri 得到 Bitmap
     * @param uri
     * @param context
     * @return
     */
    public static Bitmap decodeBitmapFromUri(Uri uri, Context context) {
        InputStream inputStream = UriUtils.openInputStream(uri, context);
        if (inputStream == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        IoUtils.close(inputStream);
        if (!(options.outWidth > 0 && options.outHeight > 0)) {
            return null;
        }

        inputStream = UriUtils.openInputStream(uri, context);
        if (inputStream == null) {
            return null;
        }
        // Canvas.getMaximumBitmapWidth() needs a hardware accelerated canvas to produce the right
        // result, so we simply use 2048x2048 instead.
        options.inSampleSize = computeInSampleSize(options, 2048, 2048);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        IoUtils.close(inputStream);
        return bitmap;
    }


    private static int computeInSampleSize(BitmapFactory.Options options, int maxWidth,
                                           int maxHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        while (width > maxWidth || height > maxHeight) {
            inSampleSize *= 2;
            width /= 2;
            height /= 2;
        }
        return inSampleSize;
    }

    // From Muzei, Copyright 2014 Google Inc.
    public static Drawable makeScrimDrawable(int baseColor, int numStops, int gravity) {
        numStops = Math.max(numStops, 2);
        PaintDrawable paintDrawable = new PaintDrawable();
        paintDrawable.setShape(new RectShape());
        final int[] stopColors = new int[numStops];

        int red = Color.red(baseColor);
        int green = Color.green(baseColor);
        int blue = Color.blue(baseColor);
        int alpha = Color.alpha(baseColor);

        for (int i = 0; i < numStops; i++) {
            float x = i * 1f / (numStops - 1);
            float opacity = MathUtils.constrain(0, 1, (float) Math.pow(x, 3));
            stopColors[i] = Color.argb((int) (alpha * opacity), red, green, blue);
        }

        final float x0, x1, y0, y1;
        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.LEFT:
                x0 = 1;
                x1 = 0;
                break;
            case Gravity.RIGHT:
                x0 = 0;
                x1 = 1;
                break;
            default:
                x0 = 0;
                x1 = 0;
                break;
        }
        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                y0 = 1;
                y1 = 0;
                break;
            case Gravity.BOTTOM:
                y0 = 0;
                y1 = 1;
                break;
            default:
                y0 = 0;
                y1 = 0;
                break;
        }

        paintDrawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(
                        width * x0,
                        height * y0,
                        width * x1,
                        height * y1,
                        stopColors, null,
                        Shader.TileMode.CLAMP);
            }
        });

        paintDrawable.setAlpha(Math.round(0.4f * 255));

        return paintDrawable;
    }

    public static Drawable makeScrimDrawable(int gravity) {
        return makeScrimDrawable(Color.BLACK, 9, gravity);
    }

    public static Drawable makeScrimDrawable() {
        return makeScrimDrawable(Gravity.BOTTOM);
    }

    public static void animateLoading(final Drawable drawable, int duration) {
        ImageLoadingEvaluator evaluator = new ImageLoadingEvaluator();
        evaluator.evaluate(0, null, null);
        final AnimateColorMatrixColorFilter filter = new AnimateColorMatrixColorFilter(
                evaluator.getColorMatrix());

        drawable.setColorFilter(filter.getColorFilter());
        ObjectAnimator animator = ObjectAnimator.ofObject(filter, "colorMatrix", evaluator,
                evaluator.getColorMatrix() /* Dummy */);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawable.setColorFilter(filter.getColorFilter());
            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    public static void animateLoading(Drawable drawable, Context context) {
        animateLoading(drawable,
                context.getResources().getInteger(android.R.integer.config_longAnimTime));
    }

    private static class ImageLoadingEvaluator implements TypeEvaluator<ColorMatrix> {
        private ColorMatrix colorMatrix = new ColorMatrix();
        private float[] elements = new float[20];

        public ColorMatrix getColorMatrix() {
            return colorMatrix;
        }

        @Override
        public ColorMatrix evaluate(float fraction, ColorMatrix startValue, ColorMatrix endValue) {

            // There are 3 phases so we multiply fraction by that amount
            float phase = fraction * 3;

            // Compute the alpha change over period [0, 2]
            float alpha = Math.min(phase, 2f) / 2f;
            elements[19] = (float) Math.round(alpha * 255);

            // We subtract to make the picture look darker, it will automatically clamp
            // This is spread over period [0, 2.5]
            final int MaxBlacker = 100;
            float blackening = (float) Math.round((1 - Math.min(phase, 2.5f) / 2.5f) * MaxBlacker);
            elements[4] = elements[9] = elements[14] = -blackening;

            // Finally we desaturate over [0, 3], taken from ColorMatrix.setSaturation
            float invSat = 1 - Math.max(0.2f, fraction);
            float R = 0.213f * invSat;
            float G = 0.715f * invSat;
            float B = 0.072f * invSat;
            elements[0] = R + fraction;
            elements[1] = G;
            elements[2] = B;
            elements[5] = R;
            elements[6] = G + fraction;
            elements[7] = B;
            elements[10] = R;
            elements[11] = G;
            elements[12] = B + fraction;

            colorMatrix.set(elements);
            return colorMatrix;
        }
    }

    private static class AnimateColorMatrixColorFilter {

        private ColorMatrixColorFilter filter;
        private ColorMatrix matrix;

        public AnimateColorMatrixColorFilter(ColorMatrix matrix) {
            setColorMatrix(matrix);
        }

        public ColorMatrixColorFilter getColorFilter() {
            return filter;
        }

        @Keep
        public ColorMatrix getColorMatrix() {
            return matrix;
        }

        @Keep
        public void setColorMatrix(ColorMatrix matrix) {
            this.matrix = matrix;
            filter = new ColorMatrixColorFilter(matrix);
        }
    }


    /**
     * 多线程压缩图片的质量
     * @param bitmap  内存中的图片
     * @param imgPath 图片的保存路径
     * @author JPH
     * @date 2014-12-5下午11:30:43
     */
    public static void compressImageByQuality(final Bitmap bitmap, final String imgPath) {
        new Thread(new Runnable() {//开启多线程进行压缩处理
            @Override
            public void run() {
                // TODO Auto-generated method stub
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
                while (baos.toByteArray().length / 1024 > 100) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
                    baos.reset();//重置baos即让下一次的写入覆盖之前的内容
                    options -= 10;//图片质量每次减少10
                    if (options < 0) options = 0;//如果图片质量小于10，则将图片的质量压缩到最小值
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
                    if (options == 0) break;//如果图片的质量已降到最低则，不再进行压缩
                }
                try {
                    FileOutputStream fos = new FileOutputStream(new File(imgPath));//将压缩后的图片保存的本地上指定路径中
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 多线程压缩图片的质量
     * @param bitmap  内存中的图片
     * @param imgPath 图片的保存路径
     * @author JPH
     * @date 2014-12-5下午11:30:43
     */
    public static boolean compressImageByQuality2(final Bitmap bitmap, final String imgPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
        while (baos.toByteArray().length / 1024 > 100) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即让下一次的写入覆盖之前的内容
            options -= 10;//图片质量每次减少10
            if (options < 0) options = 0;//如果图片质量小于10，则将图片的质量压缩到最小值
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
            if (options == 0) break;//如果图片的质量已降到最低则，不再进行压缩
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(imgPath));//将压缩后的图片保存的本地上指定路径中
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 按比例缩小图片的像素以达到压缩的目的
     *
     * @param imgPath
     * @author JPH
     * @date 2014-12-5下午11:30:59
     */
    public static Bitmap compressImageByPixel(String imgPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;
        float maxSize = 800f;//默认800px
        int be = 1;
        if (width > height && width > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
            be = (int) (newOpts.outWidth / maxSize);
        } else if (width < height && height > maxSize) {
            be = (int) (newOpts.outHeight / maxSize);
        }
        be++;
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        compressImageByQuality(bitmap, imgPath);//压缩好比例大小后再进行质量压缩
        return bitmap;
    }


    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //解码图片然后对图片进行缩放以减少内存消耗
    public static Bitmap decodeFile(String path) {
        try {
            //解码图片大小
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(path), null, o);
            //我们想要的新的图片大小
            final int REQUIRED_SIZE = 480;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            //用inSampleSize解码
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            o2.inPreferredConfig= Bitmap.Config.ARGB_4444;

            Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(path), null, o2);
            FileOutputStream fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG,30,fos);
            fos.flush();
            fos.close();
            return bitmap;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {


        }

        return null;
    }


}
