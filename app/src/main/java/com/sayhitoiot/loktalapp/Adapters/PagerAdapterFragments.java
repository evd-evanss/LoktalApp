package com.sayhitoiot.loktalapp.Adapters;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.sayhitoiot.loktalapp.Fragments.Fragment_Equipamentos;
import com.sayhitoiot.loktalapp.Fragments.Fragment_Assistencia;
import com.sayhitoiot.loktalapp.Fragments.ManuaisFragment;
import com.sayhitoiot.loktalapp.Fragments.HomeFragment;

/**
 * Criado por Evandro
 */

public class PagerAdapterFragments extends FragmentPagerAdapter {

    private Context mContext;

    public PagerAdapterFragments(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // Isto determina os fragments em cada guia
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ManuaisFragment();
            case 2:
                return new Fragment_Equipamentos();
            case 3:
                return new Fragment_Assistencia();

            default:
                return null;
                //return new Fragment_Relatorio();
        }

    }

    // Isto determina o número de páginas
    @Override
    public int getCount() {
        return 4;
    }

    // Isto determina o título das páginas
    @Override
    public CharSequence getPageTitle(int position) {
        // Gera título baseado na posição
        String titulo;
        switch (position) {
            case 0:
                return titulo = "HOME";
            case 1:
                return titulo = "MANUAIS";
            case 2:
                return titulo = "MEUS EQUIPAMENTOS";
            case 3:
                return titulo = "ASSISTÊNCIA";
            default:
                return null;
        }
    }

}
