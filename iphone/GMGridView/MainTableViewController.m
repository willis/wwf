//
//  MainTableViewController.m
//  GMGridView
//
//  Created by zwj on 12-11-21.
//  Copyright (c) 2012年 GMoledina.ca. All rights reserved.
//

#import "MainTableViewController.h"
#import "AFNetWorking.h"
#import "TDBadgedCell.h"
#import "LoginViewController.h"

@interface MainTableViewController ()

@end

@implementation MainTableViewController

- (void)viewDidLoad
{
    pageNum = 1;
    [super viewDidLoad];
    
    self.title = @"最新列表";
    
    //设置导航栏背景图片
    //[self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"minus.png"] forBarMetrics:UIBarMetricsDefault];
    [self.navigationController.navigationBar setBackgroundColor:[UIColor blackColor]];
    //导航栏正中央图片
    //UIImage * titleImage = [UIImage imageNamed:@"logo.png"];
    //UIImageView * titleview = [[UIImageView alloc]initWithImage:titleImage];
    //self.navigationItem.titleView =titleview;
    
    //绘制导航栏右侧的图片按钮
    UIImage *rightButtonImage = [UIImage imageNamed:@"refresh.png"];
    //设置按钮类型为自定义
    UIButton *rightButton = [UIButton buttonWithType:UIButtonTypeCustom];
    
    //设置按钮的显示区域
    [rightButton setFrame: CGRectMake(0, 0, 32, 32)];
    
    //设置按钮的背景显示图
    [rightButton setBackgroundImage:rightButtonImage forState:UIControlStateNormal];
    
    //设置按钮的前景显示图
    //[rightButton setImage:[UIImage imageNamed:@"house.png"] forState:UIControlStateNormal];
    //[rightButton setImage:[UIImage imageNamed:@"house.png"] forState:UIControlStateHighlighted];
    
    //监听点击事件
    [rightButton addTarget:self action:@selector(RightDown) forControlEvents:UIControlEventTouchDown];
    
    //加载导航栏中
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithCustomView:rightButton];
    
    //含义和上面类似就不详解了
    
    //绘制导航栏左侧的图片按钮
    UIImage *leftButtonImage = [UIImage imageNamed:@"house.png"];
    UIButton *leftButton = [UIButton buttonWithType:UIButtonTypeCustom];
    
    [leftButton setFrame: CGRectMake(0, 0, 32, 32)];
    [leftButton setBackgroundImage:leftButtonImage forState:UIControlStateNormal];
    //[leftButton setImage:[UIImage imageNamed:@"nav-menu-icon.png"] forState:UIControlStateNormal];
    //[leftButton setImage:[UIImage imageNamed:@"nav-menu-icon.png"] forState:UIControlStateHighlighted];
    [leftButton addTarget:self action:@selector(leftDown) forControlEvents:UIControlEventTouchDown];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc]initWithCustomView:leftButton];
    
	if (_refreshHeaderView == nil) {
		
		EGORefreshTableHeaderView *view = [[EGORefreshTableHeaderView alloc] initWithFrame:CGRectMake(0.0f, 0.0f - self.tableView.bounds.size.height, self.view.frame.size.width, self.tableView.bounds.size.height)];
		view.delegate = self;
		[self.tableView addSubview:view];
		_refreshHeaderView = view;
		
	}
	[self reloadTableViewDataSource];
	//  update the last update date
	[_refreshHeaderView refreshLastUpdatedDate];
    NSLog(@"viewDidLoad");
}


- (void)viewDidUnload
{
    NSLog(@"viewDidUnload");
    [super viewDidUnload];
    _refreshHeaderView = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [infoData count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 60;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    /*
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    if([infoData count]==0){
        return cell;
    }
    //NSLog(@"infoData %@", infoData);
    // Configure the cell.
    NSString *title = [[infoData objectAtIndex:indexPath.row] objectForKey:@"title"];
    //NSLog(@"title %@", title);
    [[cell textLabel] setText:title];
    
    NSString *pic = [[infoData objectAtIndex:indexPath.row] objectForKey:@"picPath"];
    if ([pic isEqualToString:@"nopic"]) {
        [[cell imageView] setImage:[UIImage imageNamed:@"emblem-important-red.png"]];
    }else{
        [[cell imageView] setImageWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://192.168.0.43:8080/webhtml/vx2/article/%@",pic]] placeholderImage:[UIImage imageNamed:@"emblem-important-red.png"]];
    }
    
    //cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
     */
    
    TDBadgedCell *cell = [[TDBadgedCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    
    
    if([infoData count]==0){
        return cell;
    }

    NSString *pic = [[infoData objectAtIndex:indexPath.row] objectForKey:@"picPath"];
    if ([pic isEqualToString:@"nopic"]) {
        [[cell imageView] setImage:[UIImage imageNamed:@"emblem-important-red.png"]];
    }else{
        [[cell imageView] setImageWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://192.168.0.43:8080/webhtml/vx2/article/%@",pic]] placeholderImage:[UIImage imageNamed:@"emblem-important-red.png"]];
    }
    
	cell.textLabel.text = [[infoData objectAtIndex:indexPath.row] objectForKey:@"title"];
	cell.textLabel.font = [UIFont boldSystemFontOfSize:14];
	
	cell.detailTextLabel.text = [[infoData objectAtIndex:indexPath.row] objectForKey:@"introduction"];
	cell.detailTextLabel.font = [UIFont systemFontOfSize:13];
    cell.detailTextLabel.numberOfLines = 2;
    cell.detailTextLabel.opaque = NO; // 选中Opaque表示视图后面的任何内容都不应该绘制
	
	cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
	cell.badgeString = [[infoData objectAtIndex:indexPath.row] objectForKey:@"geneContent"];
	
    /*
	if (indexPath.row == 1)
		cell.badgeColor = [UIColor colorWithRed:0.792 green:0.197 blue:0.219 alpha:1.000];
	
	if (indexPath.row == 2)
    {
		cell.badgeColor = [UIColor colorWithRed:0.197 green:0.592 blue:0.219 alpha:1.000];
        cell.badge.radius = 9;
    }
    
    if(indexPath.row == 3)
    {
        [cell setShowShadow:YES];
        
    }
     */
    return cell;
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
    NSLog(@"didSelectRowAtIndexPath");
}

#pragma mark -
#pragma mark Data Source Loading / Reloading Methods
//开始重新加载时调用的方法
- (void)reloadTableViewDataSource{
    _reloading = YES;
    //开始刷新后执行后台线程，在此之前可以开启HUD或其他对UI进行阻塞  
    [NSThread detachNewThreadSelector:@selector(doInBackground) toTarget:self withObject:nil];
}

//这个方法运行于子线程中，完成获取刷新数据的操作
-(void)doInBackground
{
    NSLog(@"doInBackground");
    
    NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:@"http://192.168.0.43:9091/pcweb/template/home/data.ajax?pageNum=%d",pageNum]];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        //NSLog(@"JSON %@",JSON);
        infoData = [[JSON objectForKey:@"data"] objectForKey:@"recArticleList"];
        //NSLog(@"infoData %@ %d",infoData , [infoData count]);
        [NSThread sleepForTimeInterval:0.3];
        
        //后台操作线程执行完后，到主线程更新UI
        [self performSelectorOnMainThread:@selector(doneLoadingTableViewData) withObject:nil waitUntilDone:YES];
        
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON){
        NSLog(@"ERROR JSON %@",error);
    }];
    [operation start];
    
}

- (void)doneLoadingTableViewData{
	
	//  model should call this when its done loading
	_reloading = NO;
	[_refreshHeaderView egoRefreshScrollViewDataSourceDidFinishedLoading:self.tableView];
    
    //刷新表格内容
    pageNum++;
    [self.tableView reloadData];
	
}


#pragma mark -
#pragma mark UIScrollViewDelegate Methods

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
	
	[_refreshHeaderView egoRefreshScrollViewDidScroll:scrollView];
    
}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
	
	[_refreshHeaderView egoRefreshScrollViewDidEndDragging:scrollView];
	
}


#pragma mark -
#pragma mark EGORefreshTableHeaderDelegate Methods

- (void)egoRefreshTableHeaderDidTriggerRefresh:(EGORefreshTableHeaderView*)view{
	
    [self reloadTableViewDataSource];
	//NSLog(@"egoRefreshTableHeaderDidTriggerRefresh");
    
}

- (BOOL)egoRefreshTableHeaderDataSourceIsLoading:(EGORefreshTableHeaderView*)view{
	//NSLog(@"egoRefreshTableHeaderDataSourceIsLoading");
	return _reloading; // should return if data source model is reloading
	
}

- (NSDate*)egoRefreshTableHeaderDataSourceLastUpdated:(EGORefreshTableHeaderView*)view{
	//NSLog(@"egoRefreshTableHeaderDataSourceLastUpdated");
    
	return [NSDate date]; // should return date data source was last changed
	
}


-(void)loginFrom{
    
}

@end
