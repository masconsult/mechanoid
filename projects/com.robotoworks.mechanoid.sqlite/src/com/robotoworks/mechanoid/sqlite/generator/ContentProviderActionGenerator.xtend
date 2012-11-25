package com.robotoworks.mechanoid.sqlite.generator

import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateTableStatement
import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateViewStatement
import com.robotoworks.mechanoid.sqlite.sqliteModel.MigrationBlock
import com.robotoworks.mechanoid.sqlite.sqliteModel.Model

import static extension com.robotoworks.mechanoid.sqlite.generator.Extensions.*
import static extension com.robotoworks.mechanoid.common.util.Strings.*
import com.robotoworks.mechanoid.sqlite.sqliteModel.ActionStatement

class ContentProviderActionGenerator {
		def dispatch CharSequence generate(Model model, CreateTableStatement tbl, boolean forId) '''
			/*
			 * Generated by Robotoworks Mechanoid
			 */
			package �model.packageName�.actions;

			import android.content.ContentValues;
			import android.content.Context;
			import android.database.sqlite.SQLiteDatabase;
			import android.database.Cursor;
			import android.net.Uri;
			import com.robotoworks.mechanoid.content.ContentProviderActions;
			import com.robotoworks.mechanoid.content.MechanoidContentProvider;
			import com.robotoworks.mechanoid.sqlite.SelectionQueryBuilder;
			import static com.robotoworks.mechanoid.sqlite.SelectionQueryBuilder.Op.*;
			
			import �model.packageName�.Abstract�model.database.name.pascalize�OpenHelper.Tables;
			import �model.packageName�.�model.database.name.pascalize�Contract.�tbl.name.pascalize�;
						
			public abstract class Abstract�tbl.name.pascalize��IF forId�ById�ENDIF�Actions extends ContentProviderActions {
				@Override
				public int delete(MechanoidContentProvider provider, Uri uri, String selection, String[] selectionArgs){
					
					
					�IF forId�
					�IF tbl.hasAndroidPrimaryKey�
					final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					final Context context = provider.getContext();
					
					int affected = new SelectionQueryBuilder()
					.expr(�tbl.name.pascalize�._ID, EQ, uri.getPathSegments().get(1))
					.append(selection, selectionArgs)
					.delete(db, Tables.�tbl.name.underscore.toUpperCase�);
					
					context.getContentResolver().notifyChange(uri, null);
					
					return affected;
					�ELSE�
					return -1; // TODO: throw exception?!
					�ENDIF�
					�ELSE�
					final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					final Context context = provider.getContext();

					int affected = db.delete(Tables.�tbl.name.underscore.toUpperCase�, selection, selectionArgs);
					context.getContentResolver().notifyChange(uri, null);
					return affected;
					�ENDIF�
				}
				
				@Override
				public Uri insert(MechanoidContentProvider provider, Uri uri, ContentValues values){
					�IF forId�
					return null; // Cannot insert on _id
					�ELSE�
					final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					final Context context = provider.getContext();

					long id = db.insertOrThrow(Tables.�tbl.name.underscore.toUpperCase�, null, values);
					context.getContentResolver().notifyChange(uri, null);
					return �tbl.name.pascalize�.buildGetByIdUri(String.valueOf(id));
				    �ENDIF�
				}
				
				@Override
				public int update(MechanoidContentProvider provider, Uri uri, ContentValues values, String selection, String[] selectionArgs){
					�IF forId�
					�IF tbl.hasAndroidPrimaryKey�
					final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					final Context context = provider.getContext();
					
					int affected = new SelectionQueryBuilder()
					.expr(�tbl.name.pascalize�._ID, EQ, uri.getPathSegments().get(1))
					.append(selection, selectionArgs)
					.update(db, Tables.�tbl.name.underscore.toUpperCase�, values);
					
					context.getContentResolver().notifyChange(uri, null);
					
					return affected;
					�ELSE�
					return -1; // TODO: throw exception?!
					�ENDIF�
					�ELSE�
					final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					final Context context = provider.getContext();
					
					int affected = db.update(Tables.�tbl.name.underscore.toUpperCase�, values, selection, selectionArgs);
					context.getContentResolver().notifyChange(uri, null);
					return affected;
					�ENDIF�
				}

				@Override
				public Cursor query(MechanoidContentProvider provider, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
					�IF forId�
					�IF tbl.hasAndroidPrimaryKey�
					final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					
					return new SelectionQueryBuilder()
					.expr(�tbl.name.pascalize�._ID, EQ, uri.getPathSegments().get(1))
					.append(selection, selectionArgs)
					.query(db, Tables.�tbl.name.underscore.toUpperCase�, projection, sortOrder);
					�ELSE�
					return null; // TODO: throw exception?!
					�ENDIF�
					�ELSE�
					final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					
					return db.query(Tables.�tbl.name.underscore.toUpperCase�, projection, selection, selectionArgs, null, null, sortOrder);
					�ENDIF�
				}
				
				@Override
			    public int bulkInsert(MechanoidContentProvider provider, Uri uri, ContentValues[] values) {
			
			    	final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
			    	
			    	int numValues = values.length;

			    	try {
			    	
				    	db.beginTransaction();
				    	
				        for (int i = 0; i < numValues; i++) {
				        	db.insertOrThrow(Tables.�tbl.name.underscore.toUpperCase�, null, values[i]);
				        }
						
						db.setTransactionSuccessful();

						
			    	} finally {
			    		db.endTransaction();
			    	}
			    	
					provider.getContext().getContentResolver().notifyChange(uri, null);
			    	
			    	return numValues;
			    }
			}
			'''

		def dispatch CharSequence generate(Model model, CreateViewStatement vw, boolean forId) '''
		/*
		 * Generated by Robotoworks Mechanoid
		 */
		package �model.packageName�.actions;

		import android.content.ContentValues;
		import android.content.ContentProvider;
		import android.database.Cursor;
		import android.database.sqlite.SQLiteDatabase;
		import android.net.Uri;
		import com.robotoworks.mechanoid.content.ContentProviderActions;
		import com.robotoworks.mechanoid.sqlite.SelectionQueryBuilder;
		import com.robotoworks.mechanoid.content.MechanoidContentProvider;
		import static com.robotoworks.mechanoid.sqlite.SelectionQueryBuilder.Op.*;
		
		import �model.packageName�.Abstract�model.database.name.pascalize�OpenHelper.Tables;
		import �model.packageName�.�model.database.name.pascalize�Contract.�vw.name.pascalize�;
					
		public abstract class Abstract�vw.name.pascalize��IF forId�ById�ENDIF�Actions extends ContentProviderActions {
			@Override
			public int delete(MechanoidContentProvider provider, Uri uri, String selection, String[] selectionArgs){
				return -1;
			}
			
			@Override
			public Uri insert(MechanoidContentProvider provider, Uri uri, ContentValues values){
				return null;
			}
			
			@Override
			public int update(MechanoidContentProvider provider, Uri uri, ContentValues values, String selection, String[] selectionArgs){
				return -1;
			}

			@Override
			public Cursor query(MechanoidContentProvider provider, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
				�IF forId�
				�IF vw.hasAndroidPrimaryKey�
				final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
					
				return new SelectionQueryBuilder()
				.expr(�vw.name.pascalize�._ID, EQ, uri.getPathSegments().get(1))
				.append(selection, selectionArgs)
				.query(db, Tables.�vw.name.underscore.toUpperCase�, projection, sortOrder);
				�ELSE�
				return null; // TODO: throw exception?!
				�ENDIF�
				�ELSE�
				final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();

				return db.query(Tables.�vw.name.underscore.toUpperCase�, projection, selection, selectionArgs, null, null, sortOrder);
				�ENDIF�
			}
		}
		'''

		def CharSequence generate(Model model, ActionStatement action) '''
		/*
		 * Generated by Robotoworks Mechanoid
		 */
		package �model.packageName�.actions;

		import com.robotoworks.mechanoid.sqlite.ContentProviderActions;
					
		public abstract class Abstract�action.name.pascalize�Actions extends ContentProviderActions {

		}
		'''
			
		def dispatch CharSequence generateStub(Model model, CreateTableStatement tbl, boolean forId) '''
				/*
				 * Generated by Robotoworks Mechanoid
				 */
			package �model.packageName�.actions;
			
			public class �tbl.name.pascalize��IF forId�ById�ENDIF�Actions extends Abstract�tbl.name.pascalize��IF forId�ById�ENDIF�Actions {}
		'''

		def dispatch CharSequence generateStub(Model model, CreateViewStatement vw, boolean forId) '''
				/*
				 * Generated by Robotoworks Mechanoid
				 */
			package �model.packageName�.actions;
			
			public class �vw.name.pascalize��IF forId�ById�ENDIF�Actions extends Abstract�vw.name.pascalize��IF forId�ById�ENDIF�Actions {}
		'''

		def CharSequence generateStub(Model model, ActionStatement action) '''
				/*
				 * Generated by Robotoworks Mechanoid
				 */
			package �model.packageName�.actions;
			
			public class �action.name.pascalize�Actions extends Abstract�action.name.pascalize�Actions {}
		'''
}