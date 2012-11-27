//
//  MainTableViewController.h
//  GMGridView
//
//  Created by zwj on 12-11-21.
//  Copyright (c) 2012年 GMoledina.ca. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EGORefreshTableHeaderView.h"

@interface MainTableViewController : UITableViewController <EGORefreshTableHeaderDelegate, UITableViewDelegate, UITableViewDataSource>{
	
	EGORefreshTableHeaderView *_refreshHeaderView;
	
	//  Reloading var should really be your tableviews datasource
	//  Putting it here for demo purposes
	BOOL _reloading;
    
    NSMutableArray *infoData;
    int pageNum;

}

- (void)reloadTableViewDataSource;
- (void)doneLoadingTableViewData;
@end

