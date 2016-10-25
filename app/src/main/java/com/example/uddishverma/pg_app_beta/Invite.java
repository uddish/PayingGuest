package com.example.uddishverma.pg_app_beta;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashankmahajan on 25/10/16.
 */



@RequiresApi(api = Build.VERSION_CODES.M)
public class Invite extends ChooserTargetService {
    @Override
        public List<ChooserTarget> onGetChooserTargets(ComponentName targetActivityName, IntentFilter matchedFilter) {


            final List<ChooserTarget> targets = new ArrayList<>();
            for (int i = 0; i< ContactsContract.lr; i++){
                final String targetName = "invite";

            }

        return ;
    }
}
