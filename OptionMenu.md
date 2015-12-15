Option Menu 사용 :

sample code :
```

    
    private static final int REVERT_ID = Menu.FIRST;
    private static final int DISCARD_ID = Menu.FIRST + 1;
    private static final int DELETE_ID = Menu.FIRST + 2;

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Build the menus that are shown when editing.
      
            menu.add(0, REVERT_ID, 0, R.string.menu_revert)
            	.setShortcut('0', 'r')
                .setIcon(android.R.drawable.ic_menu_revert);
            
            menu.add(0, DELETE_ID, 0, R.string.menu_delete)
            	.setShortcut('1', 'd')
            	.setIcon(android.R.drawable.ic_menu_delete);
            

        // Build the menus that are shown when inserting.
      
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle all of the possible menu actions.
        switch (item.getItemId()) {
        case DELETE_ID:
            
            finish();
            break;
        case DISCARD_ID:
            
            break;
        case REVERT_ID:
            
            break;
        }
        return super.onOptionsItemSelected(item);
    }    


```