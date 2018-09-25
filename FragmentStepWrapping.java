package com.zellepay.zdk.ux;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.surpriise.vouchrcommon.engine.Engine;
import com.surpriise.vouchrcommon.engine.obj.UserNetwork;
import com.surpriise.vouchrcommon.engine.obj.generics.IContact;
import com.surpriise.vouchrcreate.create.CreateConfiguration;
import com.surpriise.vouchrcreate.create.VoucherCreationActivity;
import com.surpriise.vouchrcreate.create.giftItems.AudioPersonalizationOption;
import com.surpriise.vouchrcreate.create.giftItems.ImagePersonalizationOption;
import com.surpriise.vouchrcreate.create.giftItems.NotePersonalizationOption;
import com.surpriise.vouchrcreate.create.giftItems.TipPersonalizationOption;
import com.surpriise.vouchrcreate.create.giftItems.TitlePersonalizationOption;
import com.surpriise.vouchrcreate.create.giftItems.ToPersonalizationOption;
import com.surpriise.vouchrcreate.create.giftItems.VideoPersonalizationOption;
import com.surpriise.vouchrcreate.create.giftItems.WrappingPaperPersonalizationOption;
import com.zellepay.zdk.risk.zdkConstants;

import java.util.List;

public class FragmentStepWrapping extends FragmentStepBase <FragmentStepWrapping> {
    private final String TAG = getClass().getSimpleName();
    public FragmentStepWrapping() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (super.onCreateViewWithRid(inflater, container, savedInstanceState, R.layout.fragment_step_wrapping) == null)
            return null;
        myFragId = zUX.workFlow.STEP_WRAPPING;

        if (true) {
            setStatusBarDrawable(zUX.workFlowData.mainOverlay, zUX.workFlowData.colorTransparent);

            //TextView headerTitle = (TextView) mainView.findViewById(R.id.action_wrapping_title);
            //headerTitle.setBackgroundColor(zUX.workFlowData.colorPrimary);

            buttonExit = (AppCompatImageButton) mainView.findViewById(R.id.cta_home);
            if (buttonExit != null) buttonExit.setVisibility(View.INVISIBLE);
            buttonExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.onBackPressed();
                }
            });
        }

        return mainView;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (vouchr_started == false) {
            vouchr_started = true;
            VouchrStuff();
        } else {
            utility.TransitionToNextFragment(zdkConstants.ZDK_WRAPPING_SELECTED);
        }
    }

    private boolean vouchr_started = false;
    private final String SDK_ID = "FGHZomuWpRF_yVzE-bPavw=="; // This will be given to you by Vouchr
    private final String VOUCHR_BASE_URL = " https://api.vouchrsdk.com/"; // This will be give to you by Vouchr

    private void VouchrStuff() {
        try {
            CreateConfiguration createConfiguration = new CreateConfiguration.Builder(mainContext)
                    .setGiftItems(
                            new TipPersonalizationOption(mainContext),
                            new TitlePersonalizationOption(),
                            new ToPersonalizationOption(),
                            new NotePersonalizationOption(),
                            new WrappingPaperPersonalizationOption(),
                            new ImagePersonalizationOption().setGoogleImagesEnabled(false),
                            //new GiftCardPersonalizationOption(),
                            new VideoPersonalizationOption().enableYouTube(false),
                            new AudioPersonalizationOption())
                    .build();

            Engine engine = new Engine.Builder(VOUCHR_BASE_URL, SDK_ID)
                    .setCreateConfig(createConfiguration)
                    .addAuthManager(new VouchrAuthLoginManager())
                    .addAnalyticsManagers(new VouchrEventHandler(utility))
                    .build();
            engine.startup(mainContext);

            IContact contact = new IContact() {
                @Override
                public String getContactId() {
                    return zUX.workFlowData.recipientSelected.contactID;
                }

                @Override
                public String getContactFirstName() {
                    return zUX.workFlowData.recipientSelected.contactName;
                }

                @Override
                public String getContactLastName() {
                    return null;
                }

                @Override
                public String getContactBusinessName() {
                    return null;
                }

                @Override
                public String getContactShortUsername() {
                    return zUX.workFlowData.recipientSelected.contactInitial;
                }

                @Override
                public String getContactEmail() {
                    return zUX.workFlowData.recipientSelected.contactEmail;
                }

                @Override
                public String getContactHomeCity() {
                    return null;
                }

                @Override
                public String getContactPhotoURL() {
                    return zUX.workFlowData.recipientSelected.contactPhotolUri;
                }

                @Override
                public Uri getContactLocalPhotoURI() {
                    return null;
                }

                @Override
                public List<UserNetwork> getContactExternalNetworks() {
                    return null;
                }

                @Override
                public UserNetwork getContactPrimaryUserNetwork() {
                    return null;
                }
            };

            String notes = zUX.workFlowData.notes;
            if (zUX.workFlowData.productSelected == null) {
                if (notes == null || notes.length() == 0) {
                    notes = "Happy Birthday!!!";
                }
                VoucherCreationActivity.IntentBuilder.init()
                        .addAmountInCurrency(Double.parseDouble(zUX.workFlowData.amountEntered), "USD")
                        .addContact(contact)
                        .addTitle("$" + zUX.workFlowData.amountEntered + " from Eric First")
                        //        .addWrappingPaper(wrappingPapper)
                        //        .addDate(openDate)
                        .setContestMode(false)
                        //        .addImage("imageUrl")
                        //        .addSound("soundUrl")
                        .addNote(notes)
                        .start(mainContext);
            } else {
                if (notes == null || notes.length() == 0) {
                    notes = "Happy New Year!!!";
                }
                if (zUX.workFlowData.productSelected.getImage() == null) {
                    VoucherCreationActivity.IntentBuilder.init()
                            .addAmountInCurrency(Double.parseDouble(zUX.workFlowData.amountEntered), "USD")
                            .addContact(contact)
                            .addTitle("$" + zUX.workFlowData.amountEntered + " '"+zUX.workFlowData.productSelected.getName()+"' from Eric First")
                            //        .addWrappingPaper(wrappingPapper)
                            //        .addDate(openDate)
                            .setContestMode(false)
                            //.addImage(zUX.workFlowData.productSelected.getImage())
                            //        .addSound("soundUrl")
                            .addNote(notes)
                            .start(mainContext);
                } else {
                    VoucherCreationActivity.IntentBuilder.init()
                            .addAmountInCurrency(Double.parseDouble(zUX.workFlowData.amountEntered), "USD")
                            .addContact(contact)
                            .addTitle("$" + zUX.workFlowData.amountEntered + " '"+zUX.workFlowData.productSelected.getName()+"' from Eric First")
                            //        .addWrappingPaper(wrappingPapper)
                            //        .addDate(openDate)
                            .setContestMode(false)
                            .addImage(zUX.workFlowData.productSelected.getImage())
                            //        .addSound("soundUrl")
                            .addNote(notes)
                            .start(mainContext);
                }
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            if (buttonExit != null) buttonExit.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}


/****
 09-25 09:31:21.628 10167-10167/com.zellepay.zbank.corp.lab I/WebViewFactory: Loading com.android.chrome version 69.0.3497.100 (code 349710002)
 09-25 09:31:21.677 10167-10167/com.zellepay.zbank.corp.lab I/art: Rejecting re-init on previously-failed class java.lang.Class<uJ>: java.lang.NoClassDefFoundError: Failed resolution of: Landroid/webkit/TracingController;
 at java.lang.Class java.lang.Class.classForName!(java.lang.String, boolean, java.lang.ClassLoader) (Class.java:-2)
 at java.lang.Class java.lang.Class.forName(java.lang.String, boolean, java.lang.ClassLoader) (Class.java:400)
 at java.lang.Class android.webkit.WebViewFactory.getProviderClass() (WebViewFactory.java:349)
 at android.webkit.WebViewFactoryProvider android.webkit.WebViewFactory.getProvider() (WebViewFactory.java:194)
 at java.lang.String android.webkit.WebSettings.getDefaultUserAgent(android.content.Context) (WebSettings.java:1246)
 at java.lang.String com.surpriise.vouchrcommon.engine.VouchrServer.getDefaultUserAgentString(android.content.Context) (VouchrServer.java:78)
 at void com.surpriise.vouchrcommon.engine.VouchrServer.startup(android.content.Context) (VouchrServer.java:85)
 at void com.surpriise.vouchrcommon.engine.Engine.startup(android.content.Context) (Engine.java:447)
 at void com.zellepay.zdk.ux.FragmentStepWrapping.VouchrStuff() (FragmentStepWrapping.java:94)
 at void com.zellepay.zdk.ux.FragmentStepWrapping.onResume() (FragmentStepWrapping.java:64)
 at void android.app.Fragment.performResume() (Fragment.java:2399)
 at void android.app.FragmentManagerImpl.moveToState(android.app.Fragment, int, int, int, boolean) (FragmentManager.java:1032)
 at void android.app.FragmentManagerImpl.moveToState(int, int, int, boolean) (FragmentManager.java:1171)
 at void android.app.BackStackRecord.run() (BackStackRecord.java:816)
 at boolean android.app.FragmentManagerImpl.execPendingActions() (FragmentManager.java:1578)
 at void android.app.FragmentManagerImpl$1.run() (FragmentManager.java:483)
 at void android.os.Handler.handleCallback(android.os.Message) (Handler.java:751)
 at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:95)
 at void android.os.Looper.loop() (Looper.java:154)
 at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6119)
 at java.lang.Object java.lang.reflect.Method.invoke!(java.lang.Object, java.lang.Object[]) (Method.java:-2)
 at void com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run() (ZygoteInit.java:886)
 at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:776)
 Caused by: java.lang.ClassNotFoundException: Didn't find class "android.webkit.TracingController" on path: DexPathList[[zip file "/data/app/com.android.chrome-1/base.apk"],nativeLibraryDirectories=[/data/app/com.android.chrome-1/lib/arm, /data/app/com.android.chrome-1/base.apk!/lib/armeabi-v7a, /system/lib, /vendor/lib]]
 at java.lang.Class dalvik.system.BaseDexClassLoader.findClass(java.lang.String) (BaseDexClassLoader.java:56)
 at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String, boolean) (ClassLoader.java:380)
 at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String) (ClassLoader.java:312)
 at java.lang.Class java.lang.Class.classForName!(java.lang.String, boolean, java.lang.ClassLoader) (Class.java:-2)
 at java.lang.Class java.lang.Class.forName(java.lang.String, boolean, java.lang.ClassLoader) (Class.java:400)
 at java.lang.Class android.webkit.WebViewFactory.getProviderClass() (WebViewFactory.java:349)
 at android.webkit.WebViewFactoryProvider android.webkit.WebViewFactory.getProvider() (WebViewFactory.java:194)
 at java.lang.String android.webkit.WebSettings.getDefaultUserAgent(android.content.Context) (WebSettings.java:1246)
 at java.lang.String com.surpriise.vouchrcommon.engine.VouchrServer.getDefaultUserAgentString(android.content.Context) (VouchrServer.java:78)
 at void com.surpriise.vouchrcommon.engine.VouchrServer.startup(android.content.Context) (VouchrServer.java:85)
 at void com.surpriise.vouchrcommon.engine.Engine.startup(android.content.Context) (Engine.java:447)
 at void com.zellepay.zdk.ux.FragmentStepWrapping.VouchrStuff() (FragmentStepWrapping.java:94)
 at void com.zellepay.zdk.ux.FragmentStepWrapping.onResume() (FragmentStepWrapping.java:64)
 at void android.app.Fragment.performResume() (Fragment.java:2399)
 at void android.app.FragmentManagerImpl.moveToState(android.app.Fragment, int, int, int, boolean) (FragmentManager.java:1032)
 at void android.app.FragmentManagerImpl.moveToState(int, int, int, boolean) (FragmentManager.java:1171)
 at void android.app.BackStackRecord.run() (BackStackRecord.java:816)
 at boolean android.app.FragmentManagerImpl.execPendingActions() (FragmentManager.java:1578)
 at void android.app.FragmentManagerImpl$1.run() (FragmentManager.java:483)
 at void android.os.Handler.handleCallback(android.os.Message) (Handler.java:751)
 at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:95)
 at void android.os.Looper.loop() (Looper.java:154)
 at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6119)
 at java.lang.Object java.lang.reflect.Method.invoke!(java.lang.Object, java.lang.Object[]) (Method.java:-2)
 at void com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run() (ZygoteInit.java:886)
 at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:776)
 09-25 09:31:21.697 10167-10167/com.zellepay.zbank.corp.lab I/cr_LibraryLoader: Time to load native libraries: 6 ms (timestamps 384-390)
 09-25 09:31:21.706 10167-10294/com.zellepay.zbank.corp.lab E/cr_VariationsUtils: Failed reading seed file "/data/user/0/com.zellepay.zbank.corp.lab/app_webview/variations_seed_new": /data/user/0/com.zellepay.zbank.corp.lab/app_webview/variations_seed_new (No such file or directory)
 09-25 09:31:21.707 10167-10294/com.zellepay.zbank.corp.lab E/cr_VariationsUtils: Failed reading seed file "/data/user/0/com.zellepay.zbank.corp.lab/app_webview/variations_seed": /data/user/0/com.zellepay.zbank.corp.lab/app_webview/variations_seed (No such file or directory)
 09-25 09:31:21.707 10167-10167/com.zellepay.zbank.corp.lab I/chromium: [INFO:library_loader_hooks.cc(36)] Chromium logging enabled: level = 0, default verbosity = 0
 09-25 09:31:21.707 10167-10167/com.zellepay.zbank.corp.lab I/cr_LibraryLoader: Expected native library version number "69.0.3497.100", actual native library version number "69.0.3497.100"
 09-25 09:31:21.714 10167-10294/com.zellepay.zbank.corp.lab W/System: ClassLoader referenced unknown path:
 09-25 09:31:21.715 10167-10294/com.zellepay.zbank.corp.lab D/ApplicationLoaders: ignored Vulkan layer search path /data/app/com.android.chrome-1/lib/arm:/data/app/com.android.chrome-1/base.apk!/lib/armeabi-v7a for namespace 0xa9759110
 09-25 09:31:21.751 10167-10167/com.zellepay.zbank.corp.lab I/cr_BrowserStartup: Initializing chromium process, singleProcess=true
 09-25 09:31:21.757 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: Failure getting entry for 0x7f1204c5 (t=17 e=1221) (error -2147483647)
 09-25 09:31:21.812 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: startup()
 09-25 09:31:21.864 10167-10324/com.zellepay.zbank.corp.lab E/java.lang.Class: Refreshed Token yet it is still null
 09-25 09:31:22.004 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.054 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.057 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.062 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.074 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.091 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.VoucherCreationActivity
 HIT: Loaded binding class and constructor.
 09-25 09:31:22.092 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: track(Create_Surpriise_Enter, false)
 09-25 09:31:22.281 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: track(Create_opened, false)
 09-25 09:31:22.285 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.291 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.308 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.310 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.313 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.preview.PreviewPrepopulatedView
 HIT: Loaded binding class and constructor.
 09-25 09:31:22.332 10167-10167/com.zellepay.zbank.corp.lab E/VouchrSDK: Logout called but no logoutActionHandler is set. Use Engine.Builder().setLogoutActionHandler(logoutHandler)
 09-25 09:31:22.333 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: trackScreen(Create_Surpriise)
 09-25 09:31:22.664 10167-10361/com.zellepay.zbank.corp.lab W/cr_CrashFileManager: /data/user/0/com.zellepay.zbank.corp.lab/cache/WebView/Crash Reports does not exist or is not a directory
 09-25 09:31:22.717 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.720 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.preview.PreviewPrepopulatedAdapter$PreviewItemViewHolder
 09-25 09:31:22.721 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: HIT: Loaded binding class and constructor.
 09-25 09:31:22.728 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.731 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.preview.PreviewPrepopulatedAdapter$PreviewItemViewHolder
 HIT: Cached in binding map.
 09-25 09:31:22.740 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrclaim.claim.reveal.NoteView
 HIT: Loaded binding class and constructor.
 09-25 09:31:22.753 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:31:22.755 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.preview.PreviewPrepopulatedAdapter$PreviewItemViewHolder
 HIT: Cached in binding map.
 09-25 09:31:22.761 10167-10167/com.zellepay.zbank.corp.lab I/ViewTarget: Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size of this device's screen dimensions. If you want to load the original image and are ok with the corresponding memory cost and OOMs (depending on the input size), use .override(Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and layout_height to fixed dimension, or use .override() with fixed dimensions.
 09-25 09:31:23.791 10167-10172/com.zellepay.zbank.corp.lab I/art: Do full code cache collection, code=98KB, data=121KB
 09-25 09:31:23.793 10167-10172/com.zellepay.zbank.corp.lab I/art: Starting a blocking GC JitCodeCache
 After code cache collection, code=80KB, data=75KB
 09-25 09:31:24.103 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrclaim.claim.reveal.SurpriiseTag
 09-25 09:31:24.104 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: HIT: Loaded binding class and constructor.
 09-25 09:34:00.126 10167-10172/com.zellepay.zbank.corp.lab I/art: Do partial code cache collection, code=125KB, data=125KB
 09-25 09:34:00.128 10167-10172/com.zellepay.zbank.corp.lab I/art: After code cache collection, code=124KB, data=124KB
 Increasing code cache capacity to 512KB
 09-25 09:34:00.386 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrclaim.claim.reveal.NoteView
 09-25 09:34:00.387 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: HIT: Cached in binding map.
 09-25 09:34:00.701 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 09-25 09:34:00.702 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: HIT: Loaded binding class and constructor.
 09-25 09:34:00.710 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.717 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.749 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.753 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.757 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.761 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.765 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.769 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.772 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.GiftItemsAdapter$ItemViewHolder
 HIT: Cached in binding map.
 09-25 09:34:00.774 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: track(Create_Surpriise_PersonalizationSwiped, false)
 09-25 09:34:00.923 10167-10178/com.zellepay.zbank.corp.lab I/art: Background partial concurrent mark sweep GC freed 152086(3MB) AllocSpace objects, 2(3MB) LOS objects, 32% free, 32MB/48MB, paused 1.140ms total 115.215ms
 09-25 09:34:06.544 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: track(Create_Surpriise_NextTapped, false)
 09-25 09:34:07.773 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:34:07.775 10167-10167/com.zellepay.zbank.corp.lab W/ResourceType: No known package when getting value for resource number 0xffffffff
 09-25 09:34:07.787 10167-10167/com.zellepay.zbank.corp.lab D/ButterKnife: Looking up binding for com.surpriise.vouchrcreate.create.VoucherCreationLoadingActivity
 HIT: Loaded binding class and constructor.
 09-25 09:34:08.376 10167-10167/com.zellepay.zbank.corp.lab D/ViewRootImpl[VoucherCreationLoadingActivity]: changeCanvasOpacity: opaque=true
 09-25 09:34:08.429 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: onStop()
 09-25 09:34:08.978 10167-10167/com.zellepay.zbank.corp.lab W/System.err: Error{domain='null', code=3002, i={errorEnum=UNAUTHORIZED, httpStatus=401, message=Login Failed, code=3002, errorFields=[]}, statusCode=401, message='Login Failed', displayTitle='null', displayDetail='null'}
 09-25 09:34:08.979 10167-10167/com.zellepay.zbank.corp.lab W/System.err:     at com.surpriise.vouchrcommon.engine.VouchrErrorHandlingRXCallAdapterFactory.transformException(VouchrErrorHandlingRXCallAdapterFactory.java:21)
 at com.surpriise.vouchrcommon.engine.server.ErrorHandlingRXCallAdapterFactory$RxCallAdapterWrapper.asError(ErrorHandlingRXCallAdapterFactory.java:60)
 at com.surpriise.vouchrcommon.engine.server.ErrorHandlingRXCallAdapterFactory$RxCallAdapterWrapper.access$000(ErrorHandlingRXCallAdapterFactory.java:29)
 09-25 09:34:08.980 10167-10167/com.zellepay.zbank.corp.lab W/System.err:     at com.surpriise.vouchrcommon.engine.server.ErrorHandlingRXCallAdapterFactory$RxCallAdapterWrapper$1.call(ErrorHandlingRXCallAdapterFactory.java:50)
 at com.surpriise.vouchrcommon.engine.server.ErrorHandlingRXCallAdapterFactory$RxCallAdapterWrapper$1.call(ErrorHandlingRXCallAdapterFactory.java:47)
 at rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.onError(OperatorOnErrorResumeNextViaFunction.java:140)
 09-25 09:34:08.981 10167-10167/com.zellepay.zbank.corp.lab W/System.err:     at rx.internal.operators.OperatorSubscribeOn$1$1.onError(OperatorSubscribeOn.java:59)
 at retrofit2.adapter.rxjava.OperatorMapResponseToBodyOrError$1.onNext(OperatorMapResponseToBodyOrError.java:43)
 at retrofit2.adapter.rxjava.OperatorMapResponseToBodyOrError$1.onNext(OperatorMapResponseToBodyOrError.java:38)
 at retrofit2.adapter.rxjava.RxJavaCallAdapterFactory$RequestArbiter.request(RxJavaCallAdapterFactory.java:173)
 at rx.internal.operators.OperatorSubscribeOn$1$1$1.request(OperatorSubscribeOn.java:80)
 at rx.internal.producers.ProducerArbiter.setProducer(ProducerArbiter.java:126)
 at rx.internal.operators.OperatorOnErrorResumeNextViaFunction$4.setProducer(OperatorOnErrorResumeNextViaFunction.java:159)
 at rx.internal.operators.OperatorSubscribeOn$1$1.setProducer(OperatorSubscribeOn.java:76)
 at rx.Subscriber.setProducer(Subscriber.java:205)
 at retrofit2.adapter.rxjava.RxJavaCallAdapterFactory$CallOnSubscribe.call(RxJavaCallAdapterFactory.java:152)
 at retrofit2.adapter.rxjava.RxJavaCallAdapterFactory$CallOnSubscribe.call(RxJavaCallAdapterFactory.java:138)
 at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:48)
 09-25 09:34:08.982 10167-10167/com.zellepay.zbank.corp.lab W/System.err:     at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:30)
 at rx.Observable.unsafeSubscribe(Observable.java:10144)
 at rx.internal.operators.OperatorSubscribeOn$1.call(OperatorSubscribeOn.java:94)
 at rx.internal.schedulers.CachedThreadScheduler$EventLoopWorker$1.call(CachedThreadScheduler.java:230)
 at rx.internal.schedulers.ScheduledAction.run(ScheduledAction.java:55)
 at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:428)
 at java.util.concurrent.FutureTask.run(FutureTask.java:237)
 at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:272)
 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1133)
 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607)
 at java.lang.Thread.run(Thread.java:761)
 09-25 09:34:08.991 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: track(Create_Surpriise_Confirmation_Cancel, false)
 09-25 09:34:09.909 10167-10167/com.zellepay.zbank.corp.lab D/ViewRootImpl[VoucherCreationLoadingActivity]: changeCanvasOpacity: opaque=false
 09-25 09:34:10.124 10167-10167/com.zellepay.zbank.corp.lab D/ViewRootImpl[VoucherCreationLoadingActivity]: changeCanvasOpacity: opaque=false
 09-25 09:34:10.372 10167-10167/com.zellepay.zbank.corp.lab D/ViewRootImpl[VoucherCreationLoadingActivity]: changeCanvasOpacity: opaque=false
 09-25 09:34:10.965 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: trackScreen(Create_Surpriise)
 09-25 09:34:20.987 10167-10167/com.zellepay.zbank.corp.lab D/VouchrEventHandler: onStop()
 onDestroy()
 *****/