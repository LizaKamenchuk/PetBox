package com.example.petbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class MajorActivity extends AppCompatActivity implements View.OnClickListener,
        ScrollAnimalsFragment.GoToAllaboutAnimal,
        Creation2Fragment.DataSender ,
        Creation2Fragment.BackGoing ,
        SortForAnimalsFragment.CallScrollFragment,
        SortForHelpAdsFragment.CallScrollFragment,
        FilterForHelpFragment.FilterHelpAds,
        FilterForHelpFragment.Back,
        AgeFragment.Back,
        CityFragment.Back,
        ColorFragment.Back,
        ChangeAnimalFragment.Back,
        FiltersForAnimalFragment.Back,
        AllAboutAnimalFragment.Back,
        SortForAnimalsFragment.Back,
        SortForHelpAdsFragment.Back,
        FiltersForAnimalFragment.GoToFragment,
        ChangeAnimalFragment.GoToFragment,
        DeleteDialogFragment.Delete,
        SearchFragment.GoToFragment,
        FiltersForAnimalFragment.RecreateFragments
{

private HashMap<String, androidx.fragment.app.Fragment> fragmentsMap = new HashMap<>();
private String email = null;
private User user = null;
private ControllerDB controllerDB = ControllerDB.getController();
private ControllerForSearch controllerForSearch = ControllerForSearch.getController();


    enum FragmentsNames{
    AllaboutAnimalFragment,
        AgeFragment,
    CityFragment,
    ColorFragment,
    CreateAnimalFragment,
    CreateHelpAdFragment,
    Creation2Fragment,
    FilterFragment,
    MeFragment,
    ScrollAnimalFragment,
    ScrollHelpFragment,
    SortForAnimalsFragment,
        NotEnterUserFragment,
        BookFragment,
        ScrollAnimalsMeFragment,
        ScrollHelpAdsMeFragment
}

    @Override
    protected void onStart() {
        super.onStart();
        controllerDB.setActivity(this);
        controllerForSearch.setActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Животные " + controllerDB.getAnimalsList().size() + " помощь " + controllerDB.getHelpAdsList().size() + " пользователи " + controllerForSearch.getAllUsers().size());
        if(controllerDB.getAnimalsList().size() != 0 && controllerDB.getHelpAdsList().size() != 0 && controllerForSearch.getAllUsers().size()!=0) {
            fragmentsMap.put(String.valueOf(FragmentsNames.AllaboutAnimalFragment), new AllAboutAnimalFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.AgeFragment),new AgeFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.CityFragment), new CityFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.ColorFragment), new ColorFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.CreateAnimalFragment), new CreateAnimalFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.CreateHelpAdFragment), new CreateHelpAdFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.Creation2Fragment), new Creation2Fragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.FilterFragment), new FiltersForAnimalFragment());
            //fragmentsMap.put(String.valueOf(FragmentsNames.MeFragment), new MeFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.ScrollAnimalFragment), new ScrollAnimalsFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.ScrollHelpFragment), new ScrollHelpFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.SortForAnimalsFragment), new SortForAnimalsFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.NotEnterUserFragment), new NotEnterUserFragment());
            fragmentsMap.put(String.valueOf(FragmentsNames.BookFragment), new SearchFragment());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.ScrollAnimalFragment)))
                        .addToBackStack(null)
                        .commit();
        }

        ImageButton
                home_btn = findViewById(R.id.home_btn),
                info_btn = findViewById(R.id.book_btn),
                help_btn=findViewById(R.id.help_dtn),
                message_btn=findViewById(R.id.add_btn),
                me_btn=findViewById(R.id.me_btn);
        info_btn.setOnClickListener(this);
        home_btn.setOnClickListener(this);
        help_btn.setOnClickListener(this);
        message_btn.setOnClickListener( this);
        me_btn.setOnClickListener(this);
    }
          @Override
          public void onClick(View view) {
            if(ControllerForUser.getController().getUser()!=null) {email = ControllerForUser.getController().getUser().getEmail();}
              switch (view.getId()) {
                  case R.id.home_btn:
                      getSupportFragmentManager().beginTransaction()
                          .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.ScrollAnimalFragment)))
                          .addToBackStack(null)
                          .commit();
                  break;
                  case R.id.help_dtn: getSupportFragmentManager().beginTransaction()
                          .replace(R.id.fragment_place, fragmentsMap.get(FragmentsNames.ScrollHelpFragment.toString()))
                          .addToBackStack(null)
                          .commit();
                      break;
                  case R.id.add_btn:
                      if(email == null){
                          getSupportFragmentManager().beginTransaction()
                                  .replace(R.id.fragment_place,fragmentsMap.get(FragmentsNames.NotEnterUserFragment.toString()))
                                  .addToBackStack(null)
                                  .commit();
                      }
                      else{
                          CreateAnimalFragment createAnimalFragment = new CreateAnimalFragment();
                          fragmentsMap.put(String.valueOf(FragmentsNames.CreateAnimalFragment),createAnimalFragment);
                          CreateHelpAdFragment createHelpAdFragment= new CreateHelpAdFragment();
                          fragmentsMap.put(String.valueOf(FragmentsNames.CreateHelpAdFragment),createHelpAdFragment);
                      getSupportFragmentManager().beginTransaction()
                          .replace(R.id.fragment_place, fragmentsMap.get(FragmentsNames.Creation2Fragment.toString()))
                          .addToBackStack(null)
                          .commit();
                      }
                      break;
                  case R.id.book_btn:
                      getSupportFragmentManager().beginTransaction()
                              .replace(R.id.fragment_place,fragmentsMap.get(FragmentsNames.BookFragment.toString()))
                              .addToBackStack(null)
                              .commit();
                      break;
                  case R.id.me_btn:
                      if(email == null){
                          getSupportFragmentManager().beginTransaction()
                                  .replace(R.id.fragment_place,fragmentsMap.get(FragmentsNames.NotEnterUserFragment.toString()))
                                  .addToBackStack(null)
                                  .commit();
                      }
                      else{
                          if(fragmentsMap.get(String.valueOf(FragmentsNames.MeFragment)) == null) {
                              MeFragment meFragment = new MeFragment(ControllerForUser.getController().getUser());
                              fragmentsMap.put(String.valueOf(FragmentsNames.MeFragment), meFragment);
                          }
                      getSupportFragmentManager().beginTransaction()
                              .replace(R.id.fragment_place, fragmentsMap.get(FragmentsNames.MeFragment.toString()))
                              .addToBackStack(null)
                              .commit();
                      }
                      break;
              }
          }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_enter:
                Intent intent =  new Intent(this,EnterActivity.class);
                startActivity(intent);
                break;
            case R.id.action_register:
                Intent intent1 =  new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_exit:
                ControllerForUser.getController().removeUser();
                ControllerDB.getController().clearAnimalsForMe();
                ControllerDB.getController().clearHelpAdsForMe();
                Intent intent2= new Intent(this,EnterActivity.class);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void goToAnimal(Animal animal) {
        AllAboutAnimalFragment allaboutAnimalFragment = new AllAboutAnimalFragment(animal);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_place, allaboutAnimalFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sender() {
        Creation2Fragment creation2Fragment = (Creation2Fragment) fragmentsMap.get(FragmentsNames.Creation2Fragment.toString());
        int forCreateTabPosition = creation2Fragment.getForCreateTabPosition();
        System.out.println(forCreateTabPosition);
        CreateHelpAdFragment createHelpAdFragment = (CreateHelpAdFragment) fragmentsMap.get(FragmentsNames.CreateHelpAdFragment.toString());
        CreateAnimalFragment createAnimalFragment = (CreateAnimalFragment) fragmentsMap.get(FragmentsNames.CreateAnimalFragment.toString());
        createHelpAdFragment.setPosition(forCreateTabPosition);
        createAnimalFragment.setPosition(forCreateTabPosition);
        fragmentsMap.put(FragmentsNames.CreateHelpAdFragment.toString(),new CreateHelpAdFragment());
        fragmentsMap.put(FragmentsNames.CreateAnimalFragment.toString(),new CreateAnimalFragment());
    }

    @Override
    public void goBack(Class cls) {
        System.out.println("GO BACK ACTIVITY");
        if(cls.equals(Creation2Fragment.class)){
            user = ControllerForUser.getController().getUser();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place,fragmentsMap.get(String.valueOf(FragmentsNames.ScrollAnimalFragment)))
                    .addToBackStack(null)
                    .commit();
        }
        if(cls.equals(FilterForHelpFragment.class) || cls.equals(SortForHelpAdsFragment.class)){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.ScrollHelpFragment)))
                    .addToBackStack(null)
                    .commit();
        }
        if(cls.equals(SortForAnimalsFragment.class) || cls.equals(FiltersForAnimalFragment.class)
                || cls.equals(AllAboutAnimalFragment.class)){
            System.out.println("SCROLL");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.ScrollAnimalFragment)))
                    .addToBackStack(null)
                    .commit();
        }
        if(cls.equals(AgeFragment.class) || cls.equals(ColorFragment.class) || cls.equals(CityFragment.class)){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.FilterFragment)))
                    .addToBackStack(null)
                    .commit();
        }
        if(cls.equals(ChangeAnimalFragment.class)){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.MeFragment)))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void callScrollAnimalsFragment(ArrayList<Animal> animals) {
        ScrollAnimalsFragment scrollAnimalsFragment = new ScrollAnimalsFragment(animals);
        fragmentsMap.put(String.valueOf(FragmentsNames.ScrollAnimalFragment), scrollAnimalsFragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_place, scrollAnimalsFragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void callScrollHelpAdsFragment() {
        ScrollHelpFragment scrollHelpFragment = new ScrollHelpFragment();
        fragmentsMap.put(String.valueOf(FragmentsNames.ScrollHelpFragment),scrollHelpFragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_place, scrollHelpFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void putFilterdHelpAds(ArrayList<HelpAd> helpAds) {
        ScrollHelpFragment scrollHelpFragment = new ScrollHelpFragment(helpAds);
        fragmentsMap.put(String.valueOf(FragmentsNames.ScrollHelpFragment),scrollHelpFragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_place, scrollHelpFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToFilter(Class cls) {
      if(cls.equals(AgeFragment.class)){
          getSupportFragmentManager().beginTransaction()
                  .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.AgeFragment)) )
                  .addToBackStack(null)
                  .commit();
      }
      if(cls.equals(CityFragment.class)){
          getSupportFragmentManager().beginTransaction()
                  .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.CityFragment)) )
                  .addToBackStack(null)
                  .commit();
      }
      if(cls.equals(ColorFragment.class)){
          getSupportFragmentManager().beginTransaction()
                  .replace(R.id.fragment_place, fragmentsMap.get(String.valueOf(FragmentsNames.ColorFragment)) )
                  .addToBackStack(null)
                  .commit();
      }
    }

    @Override
    public void goToScrollFragment(ArrayList<Animal> animals) {
        ScrollAnimalsFragment scrollAnimalsFragment = new ScrollAnimalsFragment(animals);
        fragmentsMap.put(String.valueOf(FragmentsNames.ScrollAnimalFragment),scrollAnimalsFragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_place, scrollAnimalsFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void goToMeFragment() {
        MeFragment meFragment = new MeFragment();
        fragmentsMap.put(String.valueOf(FragmentsNames.MeFragment),meFragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_place,meFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteHelpAdDialog(HelpAd helpAd) {
    ControllerDB.getController().deleteHelpAd(helpAd);
    goToMeFragment();
    }

    @Override
    public void deleteAnimalDialog(Animal animal) {
        ControllerDB.getController().deleteAnimal(animal);
        goToMeFragment();
    }

    @Override
    public void goToShelterPage(User user) {
        MeFragment meFragment = new MeFragment(user.getEmail());
        fragmentsMap.put(String.valueOf(FragmentsNames.MeFragment),meFragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_place,meFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void recreateAgeColorCity() {
        AgeFragment ageFragment = new AgeFragment();
        CityFragment cityFragment = new CityFragment();
        ColorFragment colorFragment = new ColorFragment();

        fragmentsMap.put(String.valueOf(FragmentsNames.AgeFragment),ageFragment);
        fragmentsMap.put(String.valueOf(FragmentsNames.CityFragment),cityFragment);
        fragmentsMap.put(String.valueOf(FragmentsNames.ColorFragment),colorFragment);

    }

}