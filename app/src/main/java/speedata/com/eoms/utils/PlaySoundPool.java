package speedata.com.eoms.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

import speedata.com.eoms.R;


public class PlaySoundPool {

	private static PlaySoundPool playSoundPool;
	private Context context;
	private SoundPool sp; //声音池
	private Map<Integer, Integer> mapSRC;


	public static PlaySoundPool getPlaySoundPool(Context context) {
		if (playSoundPool == null) {
			playSoundPool = new PlaySoundPool(context);
		}
		return playSoundPool;
	}

	private PlaySoundPool(Context context) {
		this.context = context;
		initSounds();
	}


	/**
	 * 播放声音池的声音
	 */
	public void play(int sound, int number) {
		sp.play(mapSRC.get(sound),//播放的声音资源
				1.0f,//左声道，范围为0--1.0
				1.0f,//右声道，范围为0--1.0
				0, //优先级，0为最低优先级
				number,//循环次数,0为不循环
				1);//播放速率，1为正常速率
	}
	/**
	 * 初始化
	 */
	private void initSounds() {
		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mapSRC = new HashMap<Integer, Integer>();
		mapSRC.put(1, sp.load(context, R.raw.error, 0));
		mapSRC.put(2, sp.load(context, R.raw.scan, 0));
		mapSRC.put(3, sp.load(context, R.raw.button, 0));

	}

}
