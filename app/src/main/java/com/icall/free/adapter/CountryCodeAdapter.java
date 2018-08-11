package com.icall.free.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icall.free.R;
import com.icall.free.entity.CountryCodeEntity;
import com.icall.free.util.CodeProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryCodeAdapter extends BaseAdapter {

    private Map<Integer, Integer> codeIconMap = new HashMap<>();

//    private TAFileCacheWork<View> taFileCacheWork;
    private void initCodeIcon() {
        codeIconMap.clear();
        codeIconMap.put(86, R.drawable.flag_cn);
        codeIconMap.put(244, R.drawable.flag_ao);
        codeIconMap.put(93, R.drawable.flag_af);
        codeIconMap.put(355, R.drawable.flag_al);
        codeIconMap.put(213, R.drawable.flag_dz);
        codeIconMap.put(376, R.drawable.flag_ad);
        codeIconMap.put(1264, R.drawable.flag_ai);
        codeIconMap.put(1268, R.drawable.flag_ag);
        codeIconMap.put(54, R.drawable.flag_ar);
        codeIconMap.put(374, R.drawable.flag_am);
        codeIconMap.put(247, R.drawable.flag_as);
        codeIconMap.put(61, R.drawable.flag_au);
        codeIconMap.put(43, R.drawable.flag_at);
        codeIconMap.put(994, R.drawable.flag_az);
        codeIconMap.put(1242, R.drawable.flag_bs);
        codeIconMap.put(973, R.drawable.flag_bh);
        codeIconMap.put(880, R.drawable.flag_bd);
        codeIconMap.put(1246, R.drawable.flag_bb);
        codeIconMap.put(375, R.drawable.flag_by);
        codeIconMap.put(32, R.drawable.flag_be);
        codeIconMap.put(501, R.drawable.flag_bz);
        codeIconMap.put(229, R.drawable.flag_bj);
        codeIconMap.put(1441, R.drawable.flag_bm);
        codeIconMap.put(591, R.drawable.flag_bo);
        codeIconMap.put(267, R.drawable.flag_bw);
        codeIconMap.put(55, R.drawable.flag_br);
        codeIconMap.put(673, R.drawable.flag_bn);
        codeIconMap.put(359, R.drawable.flag_bg);
        codeIconMap.put(226, R.drawable.flag_bf);
        codeIconMap.put(95, R.drawable.flag_mm);
        codeIconMap.put(257, R.drawable.flag_bi);
        codeIconMap.put(237, R.drawable.flag_cm);
        codeIconMap.put(1, R.drawable.flag_ca);
        codeIconMap.put(1345, R.drawable.flag_cc);
        codeIconMap.put(236, R.drawable.flag_cf);
        codeIconMap.put(235, R.drawable.flag_td);
        codeIconMap.put(56, R.drawable.flag_cl);
        codeIconMap.put(57, R.drawable.flag_co);
        codeIconMap.put(242, R.drawable.flag_cg);
        codeIconMap.put(682, R.drawable.flag_ck);
        codeIconMap.put(506, R.drawable.flag_cr);
        codeIconMap.put(53, R.drawable.flag_cu);
        codeIconMap.put(357, R.drawable.flag_cy);
        codeIconMap.put(420, R.drawable.flag_cz);
        codeIconMap.put(45, R.drawable.flag_dk);
        codeIconMap.put(253, R.drawable.flag_dj);
        codeIconMap.put(1890, R.drawable.flag_do);
        codeIconMap.put(593, R.drawable.flag_ec);
        codeIconMap.put(20, R.drawable.flag_eg);
        codeIconMap.put(503, R.drawable.flag_sv);
        codeIconMap.put(372, R.drawable.flag_ee);
        codeIconMap.put(251, R.drawable.flag_et);
        codeIconMap.put(679, R.drawable.flag_fj);
        codeIconMap.put(358, R.drawable.flag_fi);
        codeIconMap.put(33, R.drawable.flag_fr);
        codeIconMap.put(594, R.drawable.flag_gf);
        codeIconMap.put(241, R.drawable.flag_ga);
        codeIconMap.put(220, R.drawable.flag_gm);
        codeIconMap.put(995, R.drawable.flag_ge);
        codeIconMap.put(49, R.drawable.flag_de);
        codeIconMap.put(233, R.drawable.flag_gh);
        codeIconMap.put(350, R.drawable.flag_gi);
        codeIconMap.put(30, R.drawable.flag_gr);
        codeIconMap.put(1809, R.drawable.flag_gd);
        codeIconMap.put(1671, R.drawable.flag_gu);
        codeIconMap.put(502, R.drawable.flag_gt);
        codeIconMap.put(224, R.drawable.flag_gn);
        codeIconMap.put(592, R.drawable.flag_gy);
        codeIconMap.put(509, R.drawable.flag_ht);
        codeIconMap.put(504, R.drawable.flag_hn);
        codeIconMap.put(852, R.drawable.flag_hk);
        codeIconMap.put(36, R.drawable.flag_hu);
        codeIconMap.put(354, R.drawable.flag_is);
        codeIconMap.put(91, R.drawable.flag_in);
        codeIconMap.put(62, R.drawable.flag_id);
        codeIconMap.put(98, R.drawable.flag_ir);
        codeIconMap.put(964, R.drawable.flag_iq);
        codeIconMap.put(353, R.drawable.flag_ie);
        codeIconMap.put(972, R.drawable.flag_il);
        codeIconMap.put(39, R.drawable.flag_it);
        codeIconMap.put(225, R.drawable.flag_im);
        codeIconMap.put(1876, R.drawable.flag_jm);
        codeIconMap.put(81, R.drawable.flag_jp);
        codeIconMap.put(962, R.drawable.flag_jo);
        codeIconMap.put(855, R.drawable.flag_kh);
        codeIconMap.put(327, R.drawable.flag_kz);
        codeIconMap.put(254, R.drawable.flag_ke);
        codeIconMap.put(82, R.drawable.flag_kr);
        codeIconMap.put(965, R.drawable.flag_kw);
        codeIconMap.put(331, R.drawable.flag_kg);
        codeIconMap.put(856, R.drawable.flag_la);
        codeIconMap.put(371, R.drawable.flag_lv);
        codeIconMap.put(961, R.drawable.flag_lb);
        codeIconMap.put(266, R.drawable.flag_ls);
        codeIconMap.put(231, R.drawable.flag_lr);
        codeIconMap.put(218, R.drawable.flag_ly);
        codeIconMap.put(423, R.drawable.flag_li);
        codeIconMap.put(370, R.drawable.flag_lt);
        codeIconMap.put(352, R.drawable.flag_lu);
        codeIconMap.put(853, R.drawable.flag_mo);
        codeIconMap.put(261, R.drawable.flag_mg);
        codeIconMap.put(265, R.drawable.flag_mw);
        codeIconMap.put(60, R.drawable.flag_my);
        codeIconMap.put(960, R.drawable.flag_mv);
        codeIconMap.put(223, R.drawable.flag_ml);
        codeIconMap.put(356, R.drawable.flag_mt);
        codeIconMap.put(1670, R.drawable.flag_mp);
        codeIconMap.put(596, R.drawable.flag_me);
        codeIconMap.put(230, R.drawable.flag_mu);
        codeIconMap.put(52, R.drawable.flag_mx);
        codeIconMap.put(373, R.drawable.flag_md);
        codeIconMap.put(377, R.drawable.flag_mc);
        codeIconMap.put(976, R.drawable.flag_mn);
        codeIconMap.put(1664, R.drawable.flag_ms);
        codeIconMap.put(212, R.drawable.flag_ma);
        codeIconMap.put(258, R.drawable.flag_mz);
        codeIconMap.put(264, R.drawable.flag_na);
        codeIconMap.put(674, R.drawable.flag_nr);
        codeIconMap.put(977, R.drawable.flag_np);
        codeIconMap.put(599, R.drawable.flag_nc);
        codeIconMap.put(31, R.drawable.flag_nl);
        codeIconMap.put(64, R.drawable.flag_nz);
        codeIconMap.put(505, R.drawable.flag_ni);
        codeIconMap.put(227, R.drawable.flag_ne);
        codeIconMap.put(234, R.drawable.flag_ng);
        codeIconMap.put(850, R.drawable.flag_kp);
        codeIconMap.put(47, R.drawable.flag_no);
        codeIconMap.put(968, R.drawable.flag_om);
        codeIconMap.put(92, R.drawable.flag_pk);
        codeIconMap.put(507, R.drawable.flag_pa);
        codeIconMap.put(675, R.drawable.flag_pg);
        codeIconMap.put(595, R.drawable.flag_py);
        codeIconMap.put(51, R.drawable.flag_pe);
        codeIconMap.put(63, R.drawable.flag_ph);
        codeIconMap.put(48, R.drawable.flag_pl);
        codeIconMap.put(689, R.drawable.flag_pf);
        codeIconMap.put(351, R.drawable.flag_pt);
        codeIconMap.put(1787, R.drawable.flag_pr);
        codeIconMap.put(974, R.drawable.flag_qa);
        codeIconMap.put(262, R.drawable.flag_qa);
        codeIconMap.put(40, R.drawable.flag_ro);
        codeIconMap.put(7, R.drawable.flag_ru);
        codeIconMap.put(1758, R.drawable.flag_lc);
        codeIconMap.put(1784, R.drawable.flag_vc);
        codeIconMap.put(684, R.drawable.flag_va);
        codeIconMap.put(685, R.drawable.flag_sd);
        codeIconMap.put(378, R.drawable.flag_sm);
        codeIconMap.put(239, R.drawable.flag_st);
        codeIconMap.put(966, R.drawable.flag_sa);
        codeIconMap.put(221, R.drawable.flag_sn);
        codeIconMap.put(248, R.drawable.flag_sc);
        codeIconMap.put(232, R.drawable.flag_sl);
        codeIconMap.put(65, R.drawable.flag_sg);
        codeIconMap.put(421, R.drawable.flag_sk);
        codeIconMap.put(386, R.drawable.flag_si);
        codeIconMap.put(677, R.drawable.flag_sb);
        codeIconMap.put(252, R.drawable.flag_so);
        codeIconMap.put(27, R.drawable.flag_za);
        codeIconMap.put(34, R.drawable.flag_es);
        codeIconMap.put(94, R.drawable.flag_lk);
        codeIconMap.put(1758, R.drawable.flag_lc);
        codeIconMap.put(1784, R.drawable.flag_vc);
        codeIconMap.put(249, R.drawable.flag_sd);
        codeIconMap.put(597, R.drawable.flag_sr);
        codeIconMap.put(268, R.drawable.flag_sz);
        codeIconMap.put(46, R.drawable.flag_se);
        codeIconMap.put(41, R.drawable.flag_ch);
        codeIconMap.put(963, R.drawable.flag_sy);
        codeIconMap.put(886, R.drawable.flag_tw);
        codeIconMap.put(992, R.drawable.flag_tj);
        codeIconMap.put(255, R.drawable.flag_tz);
        codeIconMap.put(66, R.drawable.flag_th);
        codeIconMap.put(228, R.drawable.flag_tg);
        codeIconMap.put(676, R.drawable.flag_to);
        codeIconMap.put(1809, R.drawable.flag_tt);
        codeIconMap.put(216, R.drawable.flag_tn);
        codeIconMap.put(90, R.drawable.flag_tr);
        codeIconMap.put(993, R.drawable.flag_tm);
        codeIconMap.put(256, R.drawable.flag_ug);
        codeIconMap.put(380, R.drawable.flag_ua);
        codeIconMap.put(971, R.drawable.flag_ae);
        codeIconMap.put(44, R.drawable.flag_gb);
        codeIconMap.put(1, R.drawable.flag_us);
        codeIconMap.put(598, R.drawable.flag_uy);
        codeIconMap.put(233, R.drawable.flag_uz);
        codeIconMap.put(58, R.drawable.flag_ve);
        codeIconMap.put(84, R.drawable.flag_vn);
        codeIconMap.put(967, R.drawable.flag_ye);
        codeIconMap.put(381, R.drawable.flag_yu);
        codeIconMap.put(263, R.drawable.flag_zw);
        codeIconMap.put(243, R.drawable.flag_za);
        codeIconMap.put(260, R.drawable.flag_zm);

    };

    private Context mContext;
    private List<CountryCodeEntity> codeList;

    public CountryCodeAdapter(Context context) {
//        taFileCacheWork = new TAFileCacheWork<View>();
//        taFileCacheWork.setCallBackHandler(new TAStringCallBackHandler());
//        taFileCacheWork.setProcessDataHandler(new TAProcessStringHandler());
//        taFileCacheWork.setFileCache(application.getFileCache());
        this.mContext = context;
        codeList = CodeProperties.generateUrl().getCodeArray();
        initCodeIcon();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return codeList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return codeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.country_code_item, null);
            viewHolder.countryIv = (ImageView) convertView.findViewById(R.id.country_iv);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.country_name_tv);
            viewHolder.codeTv = (TextView) convertView.findViewById(R.id.country_code_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }


//        taFileCacheWork.loadFormCache(getItem(position), countryIv);
//        taFileCacheWork.loadFormCache(getItem(position), nameTv);
//        taFileCacheWork.loadFormCache(getItem(position), codeTv);

        CountryCodeEntity item = codeList.get(position);
        int code = item.getCode();
        viewHolder.nameTv.setText(item.getEn() != null ? item.getEn() : "");
        viewHolder.codeTv.setText("+" + code);
        viewHolder.countryIv.setImageResource(codeIconMap.get(code));

        return convertView;
    }

    class ViewHolder {
        TextView nameTv;
        TextView codeTv;
        ImageView countryIv;
    }

}
